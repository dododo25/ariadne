package com.dododo.ariadne.renpy.common.util;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;

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
            public void accept(LabelledGroup group) {
                acceptChainState(group);
            }

            @Override
            public void accept(CallToState callState) {
                acceptChainState(callState);
            }

            @Override
            public void accept(SwitchBranch branch) {
                acceptChainState(branch);
            }

            @Override
            public void accept(Switch aSwitch) {
                acceptState(aSwitch.getTrueBranch(), () -> aSwitch.setTrueBranch(newState));
                acceptState(aSwitch.getFalseBranch(), () -> aSwitch.setFalseBranch(newState));
            }

            @Override
            public void accept(ComplexState state) {
                if (newState == null) {
                    state.removeChild(oldState);
                } else {
                    state.replaceChild(oldState, newState);
                }
            }

            @Override
            public void accept(ComplexSwitch state) {
                accept((ComplexState) state);
            }

            private void acceptChainState(ChainState state) {
                acceptState(state.getNext(), () -> state.setNext(newState));
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
