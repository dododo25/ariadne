package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.model.SkipComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class RemoveSkipComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new RenPyFlowchartContractAdapter() {

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexOption option) {
                acceptComplexState(option);
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                acceptComplexState(branch);
            }

            @Override
            public void accept(VariableGroupComplexState complexState) {
                acceptComplexState(complexState);
            }

            @Override
            public void accept(LabelledGroupComplexState complexState) {
                acceptComplexState(complexState);
            }

            private void acceptComplexState(ComplexState state) {
                int i = 0;

                while (i < state.childrenCount()) {
                    State child = state.childAt(i);

                    if (child instanceof SkipComplexState) {
                        state.removeChild(child);
                    } else {
                        i++;
                    }
                }
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
