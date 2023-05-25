package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.State;

import java.util.HashSet;
import java.util.Set;

public abstract class StateCollector<S extends State> {

    private final FlowchartMouseFactory factory;

    protected StateCollector(FlowchartMouseFactory factory) {
        this.factory = factory;
    }

    public Set<S> collect(State flowchart) {
        Set<S> result = new HashSet<>();
        flowchart.accept(factory.createFor(state -> accept(result, state)));
        return result;
    }

    protected abstract void accept(Set<S> set, State state);
}
