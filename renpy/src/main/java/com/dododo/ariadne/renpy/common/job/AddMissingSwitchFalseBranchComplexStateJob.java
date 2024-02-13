package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    private final JaxbState rootState;

    public AddMissingSwitchFalseBranchComplexStateJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {
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
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
