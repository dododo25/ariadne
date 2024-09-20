package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class RearrangeVariableGroupStatesJob extends AbstractJob {

    private int lastInitStateIndex;

    @Override
    public void run() {
        lastInitStateIndex = 0;

        State rootState = getFlowchart();

        FlowchartContract callback = new RenPyFlowchartContractAdapter() {

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(LabelledGroupComplexState group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(ComplexOption option) {
                acceptComplexState(option);
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                acceptComplexState(branch);
            }

            private void acceptComplexState(ComplexState complexState) {
                int i = 0;

                while (i < complexState.childrenCount()) {
                    State child = complexState.childAt(i);

                    if (child instanceof VariableGroupComplexState) {
                        complexState.removeChild(child);
                        ((ComplexState) rootState).addChildAt(lastInitStateIndex++, child);
                    }

                    if (!(child instanceof VariableGroupComplexState) || complexState == rootState) {
                        i++;
                    }
                }

                if (complexState.childrenCount() == 0) {
                    complexState.addChild(new PassState());
                }
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
