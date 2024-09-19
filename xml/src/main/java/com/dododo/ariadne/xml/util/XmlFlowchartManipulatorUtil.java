package com.dododo.ariadne.xml.util;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

import java.util.stream.Stream;

public final class XmlFlowchartManipulatorUtil {

    private XmlFlowchartManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        replaceStateOnRoots(oldState, newState);

        if (newState != null) {
            newState.removeRoot(oldState);
        }
    }

    private static void replaceStateOnRoots(State oldState, State newState) {
        XmlFlowchartContract contract = new XmlFlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
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
                if (aSwitch.getTrueBranch() != null) {
                    aSwitch.setTrueBranch(newState);
                }

                if (aSwitch.getFalseBranch() != null) {
                    aSwitch.setFalseBranch(newState);
                }
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
            public void accept(ComplexOption complexOption) {
                acceptComplexState(complexOption);
            }

            @Override
            public void accept(ComplexSwitch state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                acceptComplexState(branch);
            }

            @Override
            public void accept(Marker marker) {
                acceptChainState(marker);
            }

            private void acceptChainState(ChainState state) {
                state.setNext(newState);
            }

            private void acceptComplexState(ComplexState state) {
                state.replaceChild(oldState, newState);
            }
        };

        Stream.of(oldState.getRoots())
                .forEach(root -> root.accept(contract));
    }
}
