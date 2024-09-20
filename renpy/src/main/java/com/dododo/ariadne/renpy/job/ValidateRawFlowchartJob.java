package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.exception.AriadneException;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public final class ValidateRawFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexMenu menu) {
                acceptComplexState(menu);
                validateMenuChildStates(menu);
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

            private void acceptComplexState(ComplexState state) {
                validateNonBlockingRawStates(state);
                validateSwitchStates(state);
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }

    private void validateNonBlockingRawStates(ComplexState state) {
        if (state.childrenCount() < 2) {
            return;
        }

        state.childrenStream()
                .limit(state.childrenCount() - 1L)
                .filter(child -> child instanceof GoToPoint || child instanceof EndPoint)
                .findFirst()
                .ifPresent(child -> invalidate(state, child));
    }

    private void validateMenuChildStates(ComplexMenu menu) {
        menu.childrenStream()
                .filter(child -> !(child instanceof ComplexOption))
                .findFirst()
                .ifPresent(child -> invalidate(menu, child));
    }

    private void validateSwitchStates(ComplexState state) {
        Integer lastIfRawStateIndex = null;

        for (int i = 0; i < state.childrenCount(); i++) {
            State child = state.childAt(i);

            if (child instanceof ComplexSwitchBranch && !((ComplexSwitchBranch) child).isFalseBranch()) {
                lastIfRawStateIndex = i;
            } else if (child instanceof ComplexSwitchBranch) {
                if (lastIfRawStateIndex == null) {
                    invalidate(state, child);
                }
            } else {
                lastIfRawStateIndex = null;
            }
        }
    }

    private void invalidate(State rootState, State child) {
        throw new AriadneException(String.format("Illegal child found in %s -> %s", rootState, child));
    }
}
