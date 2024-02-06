package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.State;

import java.util.HashSet;
import java.util.Set;

public abstract class StateCollector<S extends State> {

    private final FlowchartContractComposer composer;

    protected StateCollector(FlowchartContractComposer composer) {
        this.composer = composer;
    }

    public Set<S> collect(State flowchart) {
        Set<S> result = new HashSet<>();
        composer.process(flowchart, state -> accept(result, state));
        return result;
    }

    protected abstract void accept(Set<S> set, State state);
}
