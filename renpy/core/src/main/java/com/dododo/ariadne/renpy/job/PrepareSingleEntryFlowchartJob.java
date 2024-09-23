package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class PrepareSingleEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafCollector = new LeafChainStateCollector(new RenPyFlowchartMouse());

        EntryState newRootState = new EntryState();
        RootComplexState oldRootState = (RootComplexState) getFlowchart();

        for (int i = 0; i < oldRootState.childrenCount() - 1; i++) {
            State child = oldRootState.childAt(i);
            State nextChild = oldRootState.childAt(i + 1);

            leafCollector.collect(child)
                    .forEach(leaf -> leaf.setNext(nextChild));

            child.removeRoot(oldRootState);
            nextChild.removeRoot(oldRootState);
        }

        newRootState.setNext(oldRootState.childAt(0));
        setFlowchart(newRootState);
    }
}
