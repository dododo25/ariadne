package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.model.ComplexState;

public final class PrepareComplexStateEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        setFlowchart(new ComplexState());
    }
}
