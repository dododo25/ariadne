package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.model.ComplexState;

public final class PrepareSingleEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        State s = prepareFlowchart(getFlowchart());
        EntryState newRoot = new EntryState();

        newRoot.setNext(s);
        setFlowchart(newRoot);
    }

    private State prepareFlowchart(State s) {
        ComplexState complexState = (ComplexState) s;
        State firstChild = complexState.childAt(0);

        complexState.childrenStream()
                .forEach(complexState::removeChild);

        if (firstChild instanceof ComplexState) {
            return prepareFlowchart(firstChild);
        }

        return firstChild;
    }
}
