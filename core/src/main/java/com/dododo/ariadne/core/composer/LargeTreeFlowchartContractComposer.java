package com.dododo.ariadne.core.composer;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

import static com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy.DEFAULT_DEPTH;

public abstract class LargeTreeFlowchartContractComposer extends FlowchartContractComposer {

    protected LargeTreeFlowchartContractComposer() {
        super(null);
    }

    @Override
    public void process(State state, Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        process(state, callback);
    }

    @Override
    public void process(State state, FlowchartContract callback) {
        Collection<State> states = new HashSet<>();

        LargeTreeFlowchartMouseStrategy strategy = prepareStrategy(states, DEFAULT_DEPTH);
        FlowchartMouse mouse = new FlowchartMouse(callback, strategy);

        state.accept(mouse);

        while (!states.isEmpty()) {
            states.stream().findAny().ifPresent(nextState -> {
                states.remove(nextState);
                nextState.accept(mouse);
            });
        }
    }

    protected abstract LargeTreeFlowchartMouseStrategy prepareStrategy(Collection<State> states, int depth);
}
