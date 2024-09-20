package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedFlowchartManipulatorUtil;

public final class PrepareSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        ExtendedFlowchartContract callback = new ExtendedFlowchartContractAdapter() {

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                ComplexSwitchBranch switchBranch = (ComplexSwitchBranch) complexSwitch.childAt(0);
                State nextState = switchBranch.childAt(0);

                Switch rootSwitch = new Switch(switchBranch.getValue());

                rootSwitch.setTrueBranch(nextState);
                nextState.removeRoot(switchBranch);

                Switch current = rootSwitch;

                for (int i = 1; i < complexSwitch.childrenCount(); i++) {
                    switchBranch = (ComplexSwitchBranch) complexSwitch.childAt(i);
                    nextState = switchBranch.childAt(0);

                    if (switchBranch.getValue() == null) {
                        current.setFalseBranch(nextState);
                    } else {
                        Switch nextSwitch = new Switch(switchBranch.getValue());

                        nextSwitch.setTrueBranch(nextState);
                        current.setFalseBranch(nextSwitch);

                        current = nextSwitch;
                    }

                    nextState.removeRoot(switchBranch);
                }

                ExtendedFlowchartManipulatorUtil.replace(complexSwitch, rootSwitch);
            }
        };
        FlowchartMouse mouse = new ExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
