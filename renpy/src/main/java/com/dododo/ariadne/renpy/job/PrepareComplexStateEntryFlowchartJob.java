package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.extended.model.ComplexState;

public final class PrepareComplexStateEntryFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        setFlowchart(new ComplexState());
    }
}
