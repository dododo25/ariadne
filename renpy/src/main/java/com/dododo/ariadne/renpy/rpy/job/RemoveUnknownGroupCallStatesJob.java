package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(CallToState state) {
                RenPyStateManipulatorUtil.replace(state, state.getNext());
            }
        };

        mouse.accept(getFlowchart(), callback);
    }
}
