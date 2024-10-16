package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.extended.model.RootComplexState;

public final class PrepareSingleEntryFlowchartJob extends RemoveComplexStatesJob {

    @Override
    public void run() {
        EntryState newRootState = new EntryState();
        RootComplexState oldRootState = (RootComplexState) getFlowchart();

        joinChildren(oldRootState);

        newRootState.setNext(oldRootState.childAt(0));
        oldRootState.childAt(0)
                .removeRoot(oldRootState);

        setFlowchart(newRootState);
    }
}
