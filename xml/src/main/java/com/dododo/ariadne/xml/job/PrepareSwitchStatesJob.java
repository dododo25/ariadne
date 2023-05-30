package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class PrepareSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {

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

                XmlStateManipulatorUtil.replace(complexSwitch, rootSwitch);
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(callback, new ParentFirstXmlFlowchartMouseStrategy());

        getFlowchart().accept(mouse);
    }
}
