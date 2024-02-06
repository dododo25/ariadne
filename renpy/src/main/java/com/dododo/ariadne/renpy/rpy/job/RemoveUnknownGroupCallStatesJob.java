package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.composer.ParentFirstRenPyLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.job.RenPyAbstractJob;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveUnknownGroupCallStatesJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractComposer composer = selectComposerBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractComposer(),
                new RenPyFlowchartContractComposer());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(CallToState state) {
                RenPyStateManipulatorUtil.replace(state, state.getNext());
            }
        };

        composer.process(getFlowchart(), callback);
    }
}
