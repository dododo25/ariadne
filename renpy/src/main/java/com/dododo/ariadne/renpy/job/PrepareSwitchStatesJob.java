package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyStateManipulatorUtil;

public final class PrepareSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexSwitch complexSwitch) {
                process(complexSwitch);
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }

    private void process(ComplexSwitch state) {
        ComplexSwitchBranch branchState = (ComplexSwitchBranch) state.childAt(0);
        State nextState = branchState.childAt(0);

        Switch rootSwitch = new Switch(branchState.getValue());

        Switch aSwitch = rootSwitch;
        aSwitch.setTrueBranch(nextState);

        if (nextState != null) {
            nextState.removeRoot(branchState);
        }

        for (int i = 1; i < state.childrenCount(); i++) {
            branchState = (ComplexSwitchBranch) state.childAt(i);
            nextState = branchState.childAt(0);

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
