package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.job.RenPyAbstractJob;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory factory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(CallToState state) {
                RenPyStateManipulatorUtil.replace(state, state.getNext());
            }
        };

        factory.process(getFlowchart(), callback);
    }
}
