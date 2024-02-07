package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.State;

import java.util.HashSet;
import java.util.Set;

public abstract class StateCollector<S extends State> {

    private final FlowchartMouse mouse;

    protected StateCollector(FlowchartMouse mouse) {
        this.mouse = mouse;
    }

    public Set<S> collect(State flowchart) {
        Set<S> result = new HashSet<>();
        mouse.accept(flowchart, state -> accept(result, state));
        return result;
    }

    protected abstract void accept(Set<S> set, State state);
}
