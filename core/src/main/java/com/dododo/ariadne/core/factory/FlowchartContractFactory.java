package com.dododo.ariadne.core.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;

import java.util.function.Consumer;

public class FlowchartContractFactory {

    protected FlowchartMouseStrategy strategy;

    public FlowchartContractFactory() {
        this(new ParentFirstFlowchartMouseStrategy());
    }

    public FlowchartContractFactory(FlowchartMouseStrategy strategy) {
        this.strategy = strategy;
    }

    public FlowchartContract createFor(Consumer<State> consumer) {
        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return createFor(callback);
    }

    public FlowchartContract createFor(FlowchartContract callback) {
        return new FlowchartMouse(callback, strategy);
    }
}
