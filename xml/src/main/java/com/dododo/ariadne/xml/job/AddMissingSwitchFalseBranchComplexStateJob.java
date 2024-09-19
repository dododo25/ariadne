package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.PassState;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    @Override
    public void run() {
        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(ComplexSwitch complexSwitch) {
                ComplexSwitchBranch lastChild = (ComplexSwitchBranch) complexSwitch
                        .childAt(complexSwitch.childrenCount() - 1);

                if (lastChild.getValue() != null) {
                    ComplexSwitchBranch elseSwitchBranch = new ComplexSwitchBranch();
                    elseSwitchBranch.addChild(new PassState());
                    complexSwitch.addChild(elseSwitchBranch);
                }
            }
        };
        FlowchartMouse mouse = new XmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
