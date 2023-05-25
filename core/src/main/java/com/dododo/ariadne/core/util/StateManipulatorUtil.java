package com.dododo.ariadne.core.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;

import java.util.stream.Stream;

public final class StateManipulatorUtil {

    private StateManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Statement statement) {
                acceptChainState(statement);
            }

            @Override
            public void accept(Switch aSwitch) {
                process(aSwitch.getTrueBranch(), () -> aSwitch.setTrueBranch(newState));
                process(aSwitch.getFalseBranch(), () -> aSwitch.setFalseBranch(newState));
            }

            private void acceptChainState(ChainState state) {
                process(state.getNext(), () -> state.setNext(newState));
            }

            private void process(State child, Runnable runnable) {
                if (child != null && child.equals(oldState)) {
                    runnable.run();
                }
            }
        };

        Stream.of(oldState.getRoots()).forEach(root -> root.accept(contract));

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }
}
