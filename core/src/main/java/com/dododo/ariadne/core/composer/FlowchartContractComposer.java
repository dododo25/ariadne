package com.dododo.ariadne.core.composer;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;

import java.util.function.Consumer;

public class FlowchartContractComposer {

    protected final FlowchartMouseStrategy strategy;

    public FlowchartContractComposer() {
        this(new ParentFirstFlowchartMouseStrategy());
    }

    public FlowchartContractComposer(FlowchartMouseStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(State state, Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        process(state, callback);
    }

    public void process(State state, FlowchartContract callback) {
        state.accept(new FlowchartMouse(callback, strategy));
    }
}
