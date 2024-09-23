package com.dododo.ariadne.extended.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.extended.contract.ExtendedGenericFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;

import java.util.stream.Stream;

public final class ExtendedFlowchartManipulatorUtil {

    private ExtendedFlowchartManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        replaceStateOnRoots(oldState, newState);

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }

    private static void replaceStateOnRoots(State oldState, State newState) {
        FlowchartContract contract = new ExtendedGenericFlowchartContract() {

            @Override
            public void acceptChainState(ChainState state) {
                state.setNext(newState);
            }

            @Override
            public void acceptComplexState(ComplexState state) {
                if (newState == null) {
                    state.removeChild(oldState);
                } else {
                    state.replaceChild(oldState, newState);
                }
            }

            @Override
            public void accept(Switch aSwitch) {
                if (aSwitch.getTrueBranch() == oldState) {
                    aSwitch.setTrueBranch(newState);
                }

                if (aSwitch.getFalseBranch() == oldState) {
                    aSwitch.setFalseBranch(newState);
                }
            }
        };

        Stream.of(oldState.getRoots())
                .forEach(root -> root.accept(contract));
    }
}
