package com.dododo.ariadne.core.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.GenericFlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

import java.util.stream.Stream;

public final class FlowchartManipulatorUtil {

    private FlowchartManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        replaceStateOnRoots(oldState, newState);

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }

    private static void replaceStateOnRoots(State oldState, State newState) {
        FlowchartContract contract = new GenericFlowchartContract() {
            @Override
            public void acceptChainState(ChainState state) {
                process(state.getNext(), () -> state.setNext(newState));
            }

            @Override
            public void accept(Switch aSwitch) {
                process(aSwitch.getTrueBranch(), () -> aSwitch.setTrueBranch(newState));
                process(aSwitch.getFalseBranch(), () -> aSwitch.setFalseBranch(newState));
            }

            private void process(State child, Runnable runnable) {
                if (child != null && child.equals(oldState)) {
                    runnable.run();
                }
            }
        };

        Stream.of(oldState.getRoots()).forEach(root -> root.accept(contract));
    }
}
