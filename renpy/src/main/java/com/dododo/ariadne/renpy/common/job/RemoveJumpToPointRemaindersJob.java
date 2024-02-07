package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveJumpToPointRemaindersJob extends AbstractJob {

    @Override
    public void run() {
        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(JumpToPoint point) {
                RenPyStateManipulatorUtil.replace(point, new EndPoint());
            }
        };
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
