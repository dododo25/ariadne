package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

import java.util.stream.Stream;

public final class RearrangeVariableGroupStatesJob extends AbstractJob {

    private int lastVariableIndex;

    @Override
    public void run() {
        lastVariableIndex = 0;

        ComplexState rootState = (ComplexState) getFlowchart();

        FlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(VariableGroupComplexState state) {
                for (int i = 0; i < state.childrenCount(); i++) {
                    State child = state.childAt(i);

                    rootState.addChildAt(lastVariableIndex++, child);
                    child.removeRoot(state);
                }

                Stream.of(state.getRoots())
                        .forEach(root -> ((ComplexState) root).removeChild(state));
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
