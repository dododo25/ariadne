package com.dododo.ariadne.extended.util;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;

import java.util.stream.Stream;

public final class ExtendedStateManipulatorUtil {

    private ExtendedStateManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        replaceStateOnRoots(oldState, newState);
        removeStateFromChildrenRoots(oldState);

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }

    private static void replaceStateOnRoots(State oldState, State newState) {
        ExtendedFlowchartContract contract = new ExtendedFlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(CycleMarker marker) {
                acceptChainState(marker);
            }

            @Override
            public void accept(CycleEntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Text text) {
                acceptChainState(text);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainState(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainState(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainState(option);
            }

            @Override
            public void accept(Switch aSwitch) {
                acceptState(aSwitch.getTrueBranch(), () -> aSwitch.setTrueBranch(newState));
                acceptState(aSwitch.getFalseBranch(), () -> aSwitch.setFalseBranch(newState));
            }

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(PassState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(ComplexSwitch state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(SwitchBranch state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Label label) {
                acceptChainState(label);
            }

            private void acceptChainState(ChainState state) {
                acceptState(state.getNext(), () -> state.setNext(newState));
            }

            private void acceptComplexState(ComplexState state) {
                state.replaceChild(oldState, newState);

                if (oldState != null) {
                    oldState.removeRoot(state);
                }
            }

            private void acceptState(State child, Runnable runnable) {
                if (child != null && child.equals(oldState)) {
                    runnable.run();
                }
            }
        };

        Stream.of(oldState.getRoots()).forEach(root -> root.accept(contract));
    }

    private static void removeStateFromChildrenRoots(State state) {
        ExtendedFlowchartContract contract = new ExtendedFlowchartContractAdapter() {

            @Override
            public void accept(ComplexState state) {
                state.childrenStream()
                        .forEach(child -> child.removeRoot(state));
            }

            @Override
            public void accept(PassState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(SwitchBranch state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Label label) {
                acceptChainState(label);
            }

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(CycleMarker marker) {
                acceptChainState(marker);
            }

            @Override
            public void accept(CycleEntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Text text) {
                acceptChainState(text);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainState(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainState(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainState(option);
            }

            private void acceptChainState(ChainState state) {
                State next = state.getNext();

                if (next != null) {
                    next.removeRoot(state);
                }
            }
        };

        state.accept(contract);
    }
}
