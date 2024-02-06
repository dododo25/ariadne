package com.dododo.ariadne.renpy.common.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.factory.LargeTreeFlowchartContractFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.large.RenPyLargeTreeFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy.DEFAULT_DEPTH;

public abstract class RenPyLargeTreeFlowchartContractFactory extends LargeTreeFlowchartContractFactory {

    @Override
    public void process(State state, Consumer<State> consumer) {
        RenPyFlowchartContract callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        process(state, callback);
    }

    @Override
    public void process(State state, FlowchartContract callback) {
        List<State> states = new ArrayList<>();

        RenPyLargeTreeFlowchartMouseStrategy strategy =
                (RenPyLargeTreeFlowchartMouseStrategy) prepareStrategy(states, DEFAULT_DEPTH);
        RenPyFlowchartMouse mouse = new RenPyFlowchartMouse((RenPyFlowchartContract) callback, strategy);

        state.accept(mouse);

        while (!states.isEmpty()) {
            State nextState = states.remove(0);
            nextState.accept(mouse);
        }
    }
}
