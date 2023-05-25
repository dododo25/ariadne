package com.dododo.ariadne.core.factory;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;

import java.util.function.Consumer;

public class FlowchartMouseFactory {

    public FlowchartMouse createFor(Consumer<State> consumer) {
        FlowchartContract contract = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        return new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
    }
}
