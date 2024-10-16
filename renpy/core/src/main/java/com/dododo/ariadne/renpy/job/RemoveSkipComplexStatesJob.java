package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.contract.RenPyGenericFlowchartContract;
import com.dododo.ariadne.renpy.model.SkipComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class RemoveSkipComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new RenPyGenericFlowchartContract() {
            @Override
            public void acceptComplexState(ComplexState state) {
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
