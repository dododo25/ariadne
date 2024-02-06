package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class PrepareSwitchStatesJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory factory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory()
        );

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexSwitch complexSwitch) {
                process(complexSwitch);
            }
        };

        factory.process(getFlowchart(), callback);
    }

    private void process(ComplexSwitch state) {
        SwitchBranch branchState = (SwitchBranch) state.childAt(0);
        State nextState = branchState.getNext();

        Switch rootSwitch = new Switch(branchState.getValue());

        Switch aSwitch = rootSwitch;
        aSwitch.setTrueBranch(nextState);

        if (nextState != null) {
            nextState.removeRoot(branchState);
        }

        for (int i = 1; i < state.childrenCount(); i++) {
            branchState = (SwitchBranch) state.childAt(i);
            nextState = branchState.getNext();

            if (i < state.childrenCount() - 1) {
                Switch switchBranch = new Switch(branchState.getValue());
                switchBranch.setTrueBranch(nextState);

                aSwitch.setFalseBranch(switchBranch);
                aSwitch = switchBranch;
            } else {
                aSwitch.setFalseBranch(nextState);
            }

            if (nextState != null) {
                nextState.removeRoot(branchState);
            }
        }

        RenPyStateManipulatorUtil.replace(state, rootSwitch);
    }
}
