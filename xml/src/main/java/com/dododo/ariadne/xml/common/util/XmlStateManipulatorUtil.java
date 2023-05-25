package com.dododo.ariadne.xml.common.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;

import java.util.stream.Stream;

public final class XmlStateManipulatorUtil {

    private XmlStateManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        FlowchartContract contract = new XmlFlowchartContractAdapter() {

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
            public void accept(Marker marker) {
                acceptChainState(marker);
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

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }
}
