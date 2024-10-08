package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.GenericFlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

public class FlowchartMouse {

    public void accept(State state, Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    public void accept(State state, FlowchartContract callback) {
        Collection<State> blackStates = new HashSet<>();
        List<State> grayStates = new ArrayList<>();

        grayStates.add(state);

        while (!grayStates.isEmpty()) {
            State current = grayStates.remove(0);

            if (blackStates.contains(current)) {
                continue;
            }

            InnerFlowchartContract contract = prepareInnerContract(callback);

            blackStates.add(current);
            current.accept(contract);

            grayStates.addAll(0, contract.grayStates);
        }
    }

    protected InnerFlowchartContract prepareInnerContract(FlowchartContract callback) {
        return new InnerFlowchartContract(callback);
    }

    protected static class InnerFlowchartContract extends GenericFlowchartContract {

        protected final FlowchartContract callback;

        protected final Collection<State> grayStates;

        public InnerFlowchartContract(FlowchartContract callback) {
            this.callback = callback;
            this.grayStates = new ArrayList<>();
        }

        @Override
        public void acceptChainState(ChainState state) {
            state.accept(callback);

            if (state.getNext() != null) {
                grayStates.add(state.getNext());
            }
        }

        @Override
        public void accept(Menu menu) {
            menu.accept(callback);

            menu.branchesStream()
                    .forEach(grayStates::add);
        }

        @Override
        public void accept(Switch aSwitch) {
            aSwitch.accept(callback);

            if (aSwitch.getTrueBranch() != null) {
                grayStates.add(aSwitch.getTrueBranch());
            }

            if (aSwitch.getFalseBranch() != null) {
                grayStates.add(aSwitch.getFalseBranch());
            }
        }

        @Override
        public void accept(EndPoint point) {
            point.accept(callback);
        }
    }
}
