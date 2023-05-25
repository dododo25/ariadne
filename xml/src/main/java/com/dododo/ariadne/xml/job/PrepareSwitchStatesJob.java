package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
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
                SwitchBranch firstSwitchBranch = (SwitchBranch) complexSwitch.childAt(0);

                Switch rootSwitch = new Switch(firstSwitchBranch.getValue());
                rootSwitch.setTrueBranch(firstSwitchBranch.getNext());

                Switch current = rootSwitch;

                for (int i = 1; i < complexSwitch.childrenCount(); i++) {
                    SwitchBranch switchBranch = (SwitchBranch) complexSwitch.childAt(i);

                    if (switchBranch.getValue() == null) {
                        current.setFalseBranch(switchBranch.getNext());
                    } else {
                        Switch nextSwitch = new Switch(switchBranch.getValue());

                        nextSwitch.setTrueBranch(switchBranch.getNext());
                        current.setFalseBranch(nextSwitch);

                        current = nextSwitch;
                    }
                }

                XmlStateManipulatorUtil.replace(complexSwitch, rootSwitch);
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(callback, new ParentFirstXmlFlowchartMouseStrategy());

        getFlowchart().accept(mouse);
    }
}
