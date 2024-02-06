package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.composer.ParentFirstRenPyLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveJumpToPointRemaindersJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractComposer composer = selectComposerBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractComposer(),
                new RenPyFlowchartContractComposer());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(JumpToPoint point) {
                RenPyStateManipulatorUtil.replace(point, new EndPoint());
            }
        };

        composer.process(getFlowchart(), callback);
    }
}
