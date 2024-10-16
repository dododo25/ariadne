package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexSwitch complexSwitch) {
                State lastBranch = complexSwitch.childAt(complexSwitch.childrenCount() - 1);

                if (lastBranch instanceof ComplexSwitchBranch
                        && ((ComplexSwitchBranch) lastBranch).getValue() == null) {
                    return;
                }

                ComplexSwitchBranch newBranch = new ComplexSwitchBranch(false);
                newBranch.addChild(new PassState());
                complexSwitch.addChild(newBranch);
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
