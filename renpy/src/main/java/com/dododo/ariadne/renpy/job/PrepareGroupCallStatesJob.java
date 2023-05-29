package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.util.RenPyStateCopyUtil;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PrepareGroupCallStatesJob extends AbstractJob {

    private final StateCollector<LabelledGroup> subGroupCollector;

    private final StateCollector<CallToState> linkCallStateCollector;

    private final StateCollector<EndPoint> endPointStateCollector;

    private final StateCollector<ChainState> leafChainStateCollector;

    public PrepareGroupCallStatesJob() {
        FlowchartMouseFactory factory = new RenPyFlowchartMouseFactory();

        subGroupCollector = new GenericStateCollector<>(factory, LabelledGroup.class);
        linkCallStateCollector = new GenericStateCollector<>(factory, CallToState.class);
        endPointStateCollector = new GenericStateCollector<>(factory, EndPoint.class);
        leafChainStateCollector = new LeafChainStateCollector(factory);
    }

    @Override
    public void run() {
        Map<String, LabelledGroup> links = subGroupCollector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(SimpleState::getValue, Function.identity()));
        process(links);
    }

    private void process(Map<String, LabelledGroup> links) {
        Set<CallToState> states = links.values().stream()
                .flatMap(link -> linkCallStateCollector.collect(link).stream())
                .collect(Collectors.toSet());

        states.forEach(s -> process(s, links));

        if (!states.isEmpty()) {
            process(links);
        }
    }

    private void process(CallToState state, Map<String, LabelledGroup> links) {
        LabelledGroup labelledGroup = links.get(state.getValue());

        if (labelledGroup != null) {
            process(state, labelledGroup);
        }
    }

    private void process(CallToState state, LabelledGroup labelledGroup) {
        State copy = RenPyStateCopyUtil.copy(labelledGroup);
        State nextState = state.getNext();

        RenPyStateManipulatorUtil.replace(state, copy);

        leafChainStateCollector.collect(copy).forEach(leaf -> leaf.setNext(nextState));

        endPointStateCollector.collect(copy)
                .forEach(s -> RenPyStateManipulatorUtil.replace(s, nextState));

        nextState.removeRoot(state);
    }
}
