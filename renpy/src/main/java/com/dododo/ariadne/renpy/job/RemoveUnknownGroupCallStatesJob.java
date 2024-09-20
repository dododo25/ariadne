package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.util.RenPyStateManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(CallToState state) {
                RenPyStateManipulatorUtil.replace(state, state.getNext());
            }
        };

        mouse.accept(getFlowchart(), callback);
    }
}
