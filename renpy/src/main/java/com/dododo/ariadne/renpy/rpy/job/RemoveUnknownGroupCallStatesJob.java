package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<CallToState> linkCallStateStateCollector
                = new GenericStateCollector<>(new RenPyFlowchartContractFactory(), CallToState.class);
        linkCallStateStateCollector.collect(getFlowchart())
                .forEach(this::process);
    }

    private void process(CallToState state) {
        RenPyStateManipulatorUtil.replace(state, state.getNext());
    }
}
