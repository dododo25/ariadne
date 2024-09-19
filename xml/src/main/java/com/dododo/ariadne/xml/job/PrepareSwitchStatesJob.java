package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.util.XmlFlowchartManipulatorUtil;

public final class PrepareSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {

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

                XmlFlowchartManipulatorUtil.replace(complexSwitch, rootSwitch);
            }
        };
        FlowchartMouse mouse = new XmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
