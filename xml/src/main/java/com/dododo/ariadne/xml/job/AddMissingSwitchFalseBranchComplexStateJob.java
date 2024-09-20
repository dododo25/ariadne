package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;

public final class AddMissingSwitchFalseBranchComplexStateJob extends AbstractJob {

    @Override
    public void run() {
        ExtendedFlowchartContract callback = new ExtendedFlowchartContractAdapter() {
            @Override
            public void accept(ComplexSwitch complexSwitch) {
                ComplexSwitchBranch lastChild = (ComplexSwitchBranch) complexSwitch
                        .childAt(complexSwitch.childrenCount() - 1);

                if (lastChild.getValue() != null) {
                    ComplexSwitchBranch elseSwitchBranch = new ComplexSwitchBranch(true);
                    elseSwitchBranch.addChild(new PassState());
                    complexSwitch.addChild(elseSwitchBranch);
                }
            }
        };
        FlowchartMouse mouse = new ExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
