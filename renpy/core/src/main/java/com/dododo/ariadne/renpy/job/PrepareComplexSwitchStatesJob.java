package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.RootComplexState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

import java.util.stream.IntStream;

public final class PrepareComplexSwitchStatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new RenPyFlowchartContractAdapter() {

            @Override
            public void accept(RootComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexOption option) {
                acceptComplexState(option);
            }

            @Override
            public void accept(ComplexSwitchBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            @Override
            public void accept(LabelledGroupComplexState group) {
                acceptComplexState(group);
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }

    private void acceptComplexState(ComplexState state) {
        int i = 0;
        Integer fromIndex = null;

        while (i < state.childrenCount()) {
            State child = state.childAt(i);

            if (child instanceof ComplexSwitchBranch) {
                ComplexSwitchBranch switchBranch = (ComplexSwitchBranch) child;

                if (fromIndex != null && !switchBranch.isFalseBranch()) {
                    process(state, fromIndex, i);

                    i = fromIndex + 1;
                    fromIndex = null;
                }

                if (!switchBranch.isFalseBranch()) {
                    fromIndex = i;
                }
            } else if (fromIndex != null) {
                process(state, fromIndex, i);

                i = fromIndex + 1;
                fromIndex = null;
            }

            i++;
        }

        if (fromIndex != null) {
            process(state, fromIndex, state.childrenCount());
        }
    }

    private void process(ComplexState state, int from, int to) {
        ComplexSwitch complexSwitch = new ComplexSwitch();

        state.addChildAt(from, complexSwitch);

        IntStream.range(from, to).forEach(index -> {
            State child = state.childAt(from + 1);

            state.removeChild(child);
            complexSwitch.addChild(child);
        });
    }
}
