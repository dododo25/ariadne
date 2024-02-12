package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.AbstractPoint;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstFlowchartMouse extends FlowchartMouse {

    public ChildFirstFlowchartMouse() {
        super(new ChildFirstFlowchartMouseStrategy());
    }

    protected ChildFirstFlowchartMouse(ChildFirstFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void accept(State state, Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    public void accept(State state, FlowchartContract callback) {
        Collection<State> blackStates = new ArrayList<>();
        Collection<State> grayStates = prepareStartingPoints(state, blackStates);

        while (!grayStates.isEmpty()) {
            grayStates.forEach(nextState ->
                    nextState.accept(strategy, callback, new HashSet<>(), blackStates));

            grayStates = prepareStartingPoints(state, blackStates);
        }
    }

    protected Collection<State> prepareStartingPoints(State state, Collection<State> blackStates) {
        Collection<State> result = new ArrayList<>();

        FlowchartContract callback = new InnerFlowchartContract(result, blackStates);
        FlowchartMouse mouse = new ParentFirstFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    protected static class InnerFlowchartContract implements FlowchartContract {

        protected final Collection<State> result;

        protected final Collection<State> blackStates;

        public InnerFlowchartContract(Collection<State> result, Collection<State> blackStates) {
            this.result = result;
            this.blackStates = new HashSet<>(blackStates);
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

        @Override
        public void accept(Menu menu) {
            if (blackStates.contains(menu)) {
                return;
            }

            blackStates.add(menu);

            if (menu.branchesCount() == 0 || menu.branchesStream().allMatch(blackStates::contains)) {
                result.add(menu);
            }
        }

        @Override
        public void accept(Switch aSwitch) {
            if (blackStates.contains(aSwitch)) {
                return;
            }

            blackStates.add(aSwitch);

            if (blackStates.contains(aSwitch.getTrueBranch()) && blackStates.contains(aSwitch.getFalseBranch())) {
                result.add(aSwitch);
            }
        }

        @Override
        public void accept(EndPoint point) {
            acceptPoint(point);
        }

        protected void acceptChainState(ChainState state) {
            if (blackStates.contains(state)) {
                return;
            }

            blackStates.add(state);

            if (state.getNext() == null || blackStates.contains(state.getNext())) {
                result.add(state);
            }
        }

        protected void acceptPoint(AbstractPoint point) {
            if (blackStates.contains(point)) {
                return;
            }

            blackStates.add(point);
            result.add(point);
        }
    }
}
