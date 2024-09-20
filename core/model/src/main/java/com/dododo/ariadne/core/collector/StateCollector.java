package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Collection;
import java.util.HashSet;

public abstract class StateCollector<S extends State> {

    private final FlowchartMouse mouse;

    protected StateCollector(FlowchartMouse mouse) {
        this.mouse = mouse;
    }

    public Collection<S> collect(State flowchart) {
        Collection<S> result = new HashSet<>();
        mouse.accept(flowchart, state -> accept(result, state));
        return result;
    }

    protected abstract void accept(Collection<S> set, State state);
}
