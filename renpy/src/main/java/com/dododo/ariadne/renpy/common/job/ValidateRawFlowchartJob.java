package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

public final class ValidateRawFlowchartJob extends AbstractJob {

    private final JaxbState rootState;

    public ValidateRawFlowchartJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbRootState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbInitGroupState state) {
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
            public void accept(JaxbRenPyMenu menu) {
                acceptComplexState(menu);
                validateMenuChildStates(menu);
            }

            private void acceptComplexState(JaxbComplexState state) {
                validateNonBlockingRawStates(state);
                validateSwitchStates(state);
            }
        };
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }

    private void validateNonBlockingRawStates(JaxbComplexState state) {
        if (state.childrenCount() < 2) {
            return;
        }

        state.childrenStream()
                .limit(state.childrenCount() - 1L)
                .filter(child -> child instanceof JaxbGoToState || child instanceof JaxbEndState)
                .findFirst()
                .ifPresent(child -> invalidate(state, child));
    }

    private void validateMenuChildStates(JaxbRenPyMenu menu) {
        menu.childrenStream()
                .filter(child -> !(child instanceof JaxbOption))
                .findFirst()
                .ifPresent(child -> invalidate(menu, child));
    }

    private void validateSwitchStates(JaxbComplexState state) {
        Integer lastIfRawStateIndex = null;

        for (int i = 0; i < state.childrenCount(); i++) {
            JaxbState child = state.childAt(i);

            if (child instanceof JaxbSwitchBranch && !(child instanceof JaxbSwitchFalseBranch)) {
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
