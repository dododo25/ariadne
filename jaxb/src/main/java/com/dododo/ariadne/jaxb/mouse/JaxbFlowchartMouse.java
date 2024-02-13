package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

import java.util.function.Consumer;

public abstract class JaxbFlowchartMouse {

    protected final JaxbFlowchartMouseStrategy strategy;

    protected JaxbFlowchartMouse(JaxbFlowchartMouseStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void accept(JaxbState state, Consumer<JaxbState> consumer);

    public abstract void accept(JaxbState state, JaxbFlowchartContract callback);
}
