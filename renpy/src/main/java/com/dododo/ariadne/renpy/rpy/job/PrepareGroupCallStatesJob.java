package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.util.RenPyStateCopyUtil;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PrepareGroupCallStatesJob extends AbstractJob {

    @SuppressWarnings("FieldCanBeLocal")
    private StateCollector<Label> labelCollector;

    private StateCollector<CallToState> linkCallStateCollector;

    private StateCollector<EndPoint> endPointStateCollector;

    private StateCollector<ChainState> leafChainStateCollector;

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        labelCollector = new GenericStateCollector<>(mouse, Label.class);
        linkCallStateCollector = new GenericStateCollector<>(mouse, CallToState.class);
        endPointStateCollector = new GenericStateCollector<>(mouse, EndPoint.class);
        leafChainStateCollector = new LeafChainStateCollector(mouse);

        Map<String, Label> links = labelCollector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(SimpleState::getValue, Function.identity()));
        process(links);
    }

    private void process(Map<String, Label> links) {
        Set<CallToState> states = links.values().stream()
                .flatMap(link -> linkCallStateCollector.collect(link).stream())
                .collect(Collectors.toSet());

        states.forEach(s -> process(s, links));

        if (!states.isEmpty()) {
            process(links);
        }
    }

    private void process(CallToState state, Map<String, Label> links) {
        Label labelledGroup = links.get(state.getValue());

        if (labelledGroup != null) {
            process(state, labelledGroup);
        }
    }

    private void process(CallToState state, Label labelledGroup) {
        State copy = RenPyStateCopyUtil.copy(labelledGroup);
        State nextState = state.getNext();

        RenPyStateManipulatorUtil.replace(state, copy);

        leafChainStateCollector.collect(copy).forEach(leaf -> leaf.setNext(nextState));

        endPointStateCollector.collect(copy)
                .forEach(s -> RenPyStateManipulatorUtil.replace(s, nextState));

        nextState.removeRoot(state);
    }
}
