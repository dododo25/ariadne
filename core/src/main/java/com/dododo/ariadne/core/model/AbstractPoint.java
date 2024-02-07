package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;

import java.util.Collection;

public abstract class AbstractPoint extends State {

    @Override
    public final void accept(FlowchartMouseStrategy strategy, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        strategy.acceptPoint(this, callback, grayStates, blackStates);
    }
}
