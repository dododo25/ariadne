package com.dododo.ariadne.renpy.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

import java.util.stream.Stream;

public final class RenPyStateManipulatorUtil {

    private RenPyStateManipulatorUtil() {}

    public static void replace(State oldState, State newState) {
        FlowchartContract contract = new RenPyFlowchartContractAdapter() {

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
            public void accept(PassState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(CallToState callState) {
                acceptChainState(callState);
            }

            @Override
            public void accept(Marker marker) {
                acceptChainState(marker);
            }

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(VariableGroupComplexState group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(ComplexOption complexOption) {
                acceptComplexState(complexOption);
            }

            @Override
            public void accept(LabelledGroupComplexState group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(ComplexSwitch state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                acceptComplexState(branch);
            }

            private void acceptComplexState(ComplexState state) {
                if (newState == null) {
                    state.removeChild(oldState);
                } else {
                    state.replaceChild(oldState, newState);
                }
            }

            private void acceptChainState(ChainState state) {
                acceptState(state.getNext(), () -> state.setNext(newState));
            }

            @Override
            public void accept(Switch aSwitch) {
                acceptState(aSwitch.getTrueBranch(), () -> aSwitch.setTrueBranch(newState));
                acceptState(aSwitch.getFalseBranch(), () -> aSwitch.setFalseBranch(newState));
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
