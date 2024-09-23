package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class ReplaceLabelledGroupsJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<LabelledGroupComplexState> collector =
                new GenericStateCollector<>(new RenPyFlowchartMouse(), LabelledGroupComplexState.class);

        StateCollector<ChainState> leafCollector =
                new LeafChainStateCollector(new RenPyFlowchartMouse());

        collector.collect(getFlowchart()).forEach(state -> {
            Marker marker = new Marker(state.getValue());

            if (state.childrenCount() > 0) {
                State child = state.childAt(0);

                marker.setNext(child);
                child.removeRoot(state);
            }

            for (int i = 0; i < state.childrenCount() - 1; i++) {
                State child = state.childAt(i);
                State nextChild = state.childAt(i + 1);

                leafCollector.collect(child)
                        .forEach(leaf -> leaf.setNext(nextChild));

                nextChild.removeRoot(state);
            }

            RenPyFlowchartManipulatorUtil.replace(state, marker);
        });
    }
}
