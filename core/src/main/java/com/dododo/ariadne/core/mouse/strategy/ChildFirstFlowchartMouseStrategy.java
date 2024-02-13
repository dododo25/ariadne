package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.AbstractPoint;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;

import java.util.Arrays;
import java.util.Collection;

public class ChildFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {

    @Override
    public void acceptChainState(ChainState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(state);

        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        acceptRoots(state, grayStates, blackStates);
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(menu);

        if (blackStates.contains(menu)) {
            return;
        }

        blackStates.add(menu);
        callback.accept(menu);

        acceptRoots(menu, grayStates, blackStates);
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(aSwitch);

        if (blackStates.contains(aSwitch)) {
            return;
        }

        blackStates.add(aSwitch);
        callback.accept(aSwitch);

        acceptRoots(aSwitch, grayStates, blackStates);
    }

    @Override
    public void acceptPoint(AbstractPoint point, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(point);

        if (blackStates.contains(point)) {
            return;
        }

        blackStates.add(point);
        point.accept(callback);

        acceptRoots(point, grayStates, blackStates);
    }

    protected void acceptRoots(State state, Collection<State> grayStates, Collection<State> blackStates) {
        FlowchartContract contract = new InnerFlowchartContract(grayStates, blackStates);

        Arrays.stream(state.getRoots())
                .forEach(root -> root.accept(contract));
    }

    protected static class InnerFlowchartContract extends FlowchartContractAdapter {

        protected final Collection<State> grayStates;

        protected final Collection<State> blackStates;

        protected InnerFlowchartContract(Collection<State> grayStates, Collection<State> blackStates) {
            this.grayStates = grayStates;
            this.blackStates = blackStates;
        }

        @Override
        public void accept(EntryState state) {
            acceptChainState(state);
        }

        @Override
        public void accept(CycleEntryState state) {
            acceptChainState(state);
        }

        @Override
        public void accept(CycleMarker marker) {
            acceptChainState(marker);
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
        public void accept(Menu menu) {
            if (menu.branchesCount() == 0 || menu.branchesStream().allMatch(blackStates::contains)) {
                grayStates.add(menu);
            }
        }

        @Override
        public void accept(Switch aSwitch) {
            if (aSwitch.getTrueBranch() == null && aSwitch.getFalseBranch() == null
                    || blackStates.contains(aSwitch.getTrueBranch())
                    || blackStates.contains(aSwitch.getFalseBranch())) {
                grayStates.add(aSwitch);
            }
        }

        protected void acceptChainState(ChainState state) {
            if (state.getNext() == null || blackStates.contains(state.getNext())) {
                grayStates.add(state);
            }
        }
    }
}
