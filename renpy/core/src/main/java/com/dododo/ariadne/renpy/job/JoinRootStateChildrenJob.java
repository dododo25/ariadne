package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class JoinRootStateChildrenJob extends AbstractJob {

    private final StateCollector<ChainState> leafCollector;

    private final StateCollector<Switch> switchCollector;

    public JoinRootStateChildrenJob() {
        this.leafCollector = new LeafChainStateCollector(new RenPyFlowchartMouse());
        this.switchCollector = new GenericStateCollector<>(new RenPyFlowchartMouse(), Switch.class);
    }

    @Override
    public void run() {
        RootComplexState rootState = (RootComplexState) getFlowchart();

        for (int i = 0; i < rootState.childrenCount() - 1; i++) {
            State child = rootState.childAt(i);
            State nextChild = rootState.childAt(i + 1);

            leafCollector.collect(child)
                    .forEach(leaf -> leaf.setNext(nextChild));

            switchCollector.collect(child).forEach(aSwitch -> {
                if (aSwitch.getTrueBranch() == null) {
                    aSwitch.setTrueBranch(nextChild);
                }

                if (aSwitch.getFalseBranch() == null) {
                    aSwitch.setFalseBranch(nextChild);
                }
            });
        }
    }
}