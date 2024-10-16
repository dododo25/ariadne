package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.extended.model.RootComplexState;

public final class PrepareComplexStateEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        setFlowchart(new RootComplexState());
    }
}
