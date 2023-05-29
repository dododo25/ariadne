package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    private final JaxbState rootState;

    public AddMissingSwitchFalseBranchComplexStateJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbComplexSwitch aSwitch) {
                JaxbState lastBranch = aSwitch.childAt(aSwitch.childrenCount() - 1);

                if (lastBranch instanceof JaxbSwitchFalseBranch
                        && ((JaxbSwitchFalseBranch) lastBranch).getValue() == null) {
                    return;
                }

                JaxbSwitchFalseBranch newBranch = new JaxbSwitchFalseBranch();
                newBranch.addChild(new JaxbPassState());
                aSwitch.addChild(newBranch);
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
