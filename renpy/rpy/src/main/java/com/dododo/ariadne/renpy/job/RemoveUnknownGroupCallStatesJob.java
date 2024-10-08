package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyFlowchartManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        FlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(CallToState state) {
                RenPyFlowchartManipulatorUtil.replace(state, state.getNext());
            }
        };

        mouse.accept(getFlowchart(), callback);
    }
}
