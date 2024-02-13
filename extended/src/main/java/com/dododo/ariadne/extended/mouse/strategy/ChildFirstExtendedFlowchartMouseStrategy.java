package com.dododo.ariadne.extended.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;

import java.util.Arrays;
import java.util.Collection;

public class ChildFirstExtendedFlowchartMouseStrategy extends ChildFirstFlowchartMouseStrategy
        implements ExtendedFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(ComplexState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(state);

        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        acceptRoots(state, grayStates, blackStates);
    }

    @Override
    protected void acceptRoots(State state, Collection<State> grayStates, Collection<State> blackStates) {
        FlowchartContract contract = new InnerExtendedFlowchartContract(grayStates, blackStates);

        Arrays.stream(state.getRoots())
                .forEach(root -> root.accept(contract));
    }

    protected static class InnerExtendedFlowchartContract extends InnerFlowchartContract
            implements ExtendedFlowchartContract {

        protected InnerExtendedFlowchartContract(Collection<State> grayStates, Collection<State> blackStates) {
            super(grayStates, blackStates);
        }

        @Override
        public void accept(ComplexState state) {
            if (state.childrenCount() == 0 || state.childrenStream().allMatch(blackStates::contains)) {
                grayStates.add(state);
            }
        }

        @Override
        public void accept(ComplexSwitch complexSwitch) {
            accept((ComplexState) complexSwitch);
        }

        @Override
        public void accept(SwitchBranch branch) {
            acceptChainState(branch);
        }

        @Override
        public void accept(Label label) {
            acceptChainState(label);
        }

        @Override
        public void accept(PassState state) {
            acceptChainState(state);
        }

        @Override
        public void accept(GoToPoint point) {
            throw new UnsupportedOperationException();
        }
    }
}
