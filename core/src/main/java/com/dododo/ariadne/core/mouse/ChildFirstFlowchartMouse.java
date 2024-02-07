package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
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

    public ChildFirstFlowchartMouse(ChildFirstFlowchartMouseStrategy strategy) {
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
        Collection<State> grayStates = new ArrayList<>(prepareStartingPoints(state));
        Collection<State> blackStates = new ArrayList<>();

        while (!grayStates.isEmpty()) {
            grayStates.stream().findFirst().ifPresent(nextState -> {
                grayStates.remove(nextState);
                nextState.accept(strategy, callback, grayStates, blackStates);
            });
        }
    }

    protected Collection<State> prepareStartingPoints(State state) {
        Collection<State> result = new HashSet<>();

        FlowchartContract callback = new InnerFlowchartContract(result);
        ParentFirstFlowchartMouse mouse = new ParentFirstFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    protected static class InnerFlowchartContract implements FlowchartContract {

        protected final Collection<State> result;

        public InnerFlowchartContract(Collection<State> result) {
            this.result = result;
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
            if (menu.branchesCount() == 0) {
                result.add(menu);
            }

            menu.branchesStream().forEach(option -> {
                State nextState = option.getNext();

                if (nextState == menu) {
                    result.add(option);
                }
            });
        }

        @Override
        public void accept(Switch aSwitch) {
            if (aSwitch.getTrueBranch() == null && aSwitch.getFalseBranch() == null
                    || aSwitch.getTrueBranch() == aSwitch
                    || aSwitch.getFalseBranch() == aSwitch) {
                result.add(aSwitch);
            }
        }

        @Override
        public void accept(EndPoint point) {
            result.add(point);
        }

        protected void acceptChainState(ChainState state) {
            if (state.getNext() == null || state.getNext() == state) {
                result.add(state);
            }
        }
    }
}
