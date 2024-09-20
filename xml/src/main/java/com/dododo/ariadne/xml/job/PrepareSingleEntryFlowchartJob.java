package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.EntryState;

public final class PrepareSingleEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        EntryState newRoot = new EntryState();
        newRoot.setNext(getFlowchart());
        setFlowchart(newRoot);
    }
}
