package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.mouse.ParentFirstJaxbFlowchartMouse;

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
        ParentFirstJaxbFlowchartMouse mouse = new ParentFirstJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
