package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveJumpToPointRemaindersJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory factory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(JumpToPoint point) {
                RenPyStateManipulatorUtil.replace(point, new EndPoint());
            }
        };

        factory.process(getFlowchart(), callback);
    }
}
