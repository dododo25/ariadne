package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.State;

import java.util.HashSet;
import java.util.Set;

public abstract class StateCollector<S extends State> {

    private final FlowchartContractFactory factory;

    protected StateCollector(FlowchartContractFactory factory) {
        this.factory = factory;
    }

    public Set<S> collect(State flowchart) {
        Set<S> result = new HashSet<>();
        factory.process(flowchart, state -> accept(result, state));
        return result;
    }

    protected abstract void accept(Set<S> set, State state);
}
