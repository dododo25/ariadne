package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.SwitchBranch;
import com.dododo.ariadne.extended.mouse.ParentFirstExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedStateManipulatorUtil;

public final class PrepareSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        ExtendedFlowchartContract callback = new ExtendedFlowchartContractAdapter() {

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                SwitchBranch switchBranch = (SwitchBranch) complexSwitch.childAt(0);
                State nextState = switchBranch.getNext();

                Switch rootSwitch = new Switch(switchBranch.getValue());
                rootSwitch.setTrueBranch(switchBranch.getNext());

                nextState.removeRoot(switchBranch);

                Switch current = rootSwitch;

                for (int i = 1; i < complexSwitch.childrenCount(); i++) {
                    switchBranch = (SwitchBranch) complexSwitch.childAt(i);
                    nextState = switchBranch.getNext();

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

                ExtendedStateManipulatorUtil.replace(complexSwitch, rootSwitch);
            }
        };
        FlowchartMouse mouse = new ParentFirstExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
