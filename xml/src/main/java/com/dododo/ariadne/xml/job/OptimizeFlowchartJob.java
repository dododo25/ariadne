package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ChildFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class OptimizeFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        XmlFlowchartContract contract = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                State trueBranch = aSwitch.getTrueBranch();
                State falseBranch = aSwitch.getFalseBranch();

                if (trueBranch == falseBranch) {
                    if (aSwitch == trueBranch) {
                        XmlStateManipulatorUtil.replace(aSwitch, new EndPoint());
                    } else {
                        XmlStateManipulatorUtil.replace(aSwitch, trueBranch);
                    }
                } else if (aSwitch == trueBranch) {
                    XmlStateManipulatorUtil.replace(aSwitch, falseBranch);
                } else if (aSwitch == falseBranch) {
                    XmlStateManipulatorUtil.replace(aSwitch, trueBranch);
                }
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(contract, new ChildFirstXmlFlowchartMouseStrategy());
        getFlowchart().accept(mouse);
    }
}
