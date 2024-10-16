package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;
import com.dododo.ariadne.renpy.util.RenPyStateCopyUtil;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ProcessGroupCallStatesJob extends AbstractJob {

    @SuppressWarnings("FieldCanBeLocal")
    private StateCollector<Marker> markerStatesCollector;

    private StateCollector<CallToState> linkCallStateCollector;

    private StateCollector<EndPoint> endPointStateCollector;

    private StateCollector<ChainState> leafChainStateCollector;

    @Override
    public void run() {
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        markerStatesCollector = new GenericStateCollector<>(mouse, Marker.class);
        linkCallStateCollector = new GenericStateCollector<>(mouse, CallToState.class);
        endPointStateCollector = new GenericStateCollector<>(mouse, EndPoint.class);
        leafChainStateCollector = new LeafChainStateCollector(mouse);

        Map<String, Marker> markers = markerStatesCollector.collect(getFlowchart())
                .stream()
                .collect(Collectors.toMap(Marker::getValue, Function.identity()));
        process(markers);
    }

    private void process(Map<String, Marker> markers) {
        Collection<CallToState> states = markers.values()
                .stream()
                .flatMap(link -> linkCallStateCollector.collect(link).stream())
                .collect(Collectors.toSet());

        states.forEach(s -> process(s, markers));

        if (!states.isEmpty()) {
            process(markers);
        }
    }

    private void process(CallToState state, Map<String, Marker> markers) {
        Marker marker = markers.get(state.getValue());

        if (marker == null) {
            return;
        }

        State copy = RenPyStateCopyUtil.copy(marker);
        State nextState = state.getNext();

        RenPyFlowchartManipulatorUtil.replace(state, copy);

        leafChainStateCollector.collect(copy)
                .forEach(leaf -> leaf.setNext(nextState));
        endPointStateCollector.collect(copy)
                .forEach(s -> RenPyFlowchartManipulatorUtil.replace(s, nextState));

         nextState.removeRoot(state);
    }
}
