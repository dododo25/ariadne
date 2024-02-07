package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;

import java.util.function.Consumer;

public abstract class FlowchartMouse {

    protected FlowchartMouseStrategy strategy;

    protected FlowchartMouse(FlowchartMouseStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void accept(State state, Consumer<State> consumer);

    public abstract void accept(State state, FlowchartContract callback);
}
