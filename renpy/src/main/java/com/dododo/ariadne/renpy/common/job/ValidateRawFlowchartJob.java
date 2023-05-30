package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

public final class ValidateRawFlowchartJob extends AbstractJob {

    private final JaxbState rootState;

    public ValidateRawFlowchartJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbGroupState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbOption option) {
                acceptComplexState(option);
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            @Override
            public void accept(JaxbSwitchFalseBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            @Override
            public void accept(JaxbLabelledGroup group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(JaxbMenu menu) {
                acceptComplexState(menu);
                validateMenuChildStates(menu);
            }

            private void acceptComplexState(JaxbComplexState state) {
                validateNonBlockingRawStates(state);
                validateSwitchStates(state);
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }

    private void validateNonBlockingRawStates(JaxbComplexState state) {
        if (state.childrenCount() < 2) {
            return;
        }

        state.childrenStream()
                .limit(state.childrenCount() - 1L)
                .filter(child -> child instanceof JaxbJumpToState || child instanceof JaxbEndState)
                .findFirst()
                .ifPresent(child -> invalidate(state, child));
    }

    private void validateMenuChildStates(JaxbMenu menu) {
        menu.childrenStream()
                .filter(child -> !(child instanceof JaxbOption))
                .findFirst()
                .ifPresent(child -> invalidate(menu, child));
    }

    private void validateSwitchStates(JaxbComplexState state) {
        Integer lastIfRawStateIndex = null;

        for (int i = 0; i < state.childrenCount(); i++) {
            JaxbState child = state.childAt(i);

            if (child instanceof JaxbSwitchBranch) {
                lastIfRawStateIndex = i;
            } else if (child instanceof JaxbSwitchFalseBranch) {
                if (lastIfRawStateIndex == null) {
                    invalidate(state, child);
                }
            } else {
                lastIfRawStateIndex = null;
            }
        }
    }

    private void invalidate(JaxbState rootState, JaxbState child) {
        throw new AriadneException(String.format("Illegal child found in %s -> %s", rootState, child));
    }
}
