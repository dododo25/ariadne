package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.xml.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    private final JaxbState rootState;

    public AddMissingSwitchFalseBranchComplexStateJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbComplexSwitch complexSwitch) {
                JaxbSwitchBranch lastChild = (JaxbSwitchBranch) complexSwitch
                        .childAt(complexSwitch.childrenCount() - 1);

                if (lastChild.getValue() != null) {
                    JaxbSwitchBranch elseSwitchBranch = new JaxbSwitchBranch();
                    elseSwitchBranch.addChild(new JaxbPassState());
                    complexSwitch.addChild(elseSwitchBranch);
                }
            }
        };

        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
