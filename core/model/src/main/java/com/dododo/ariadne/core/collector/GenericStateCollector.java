package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Collection;

public class GenericStateCollector<S extends State> extends StateCollector<S> {

    private final Class<S> type;

    public GenericStateCollector(FlowchartMouse mouse, Class<S> type) {
        super(mouse);
        this.type = type;
    }

    @Override
    public void accept(Collection<S> set, State state) {
        if (type.isAssignableFrom(state.getClass())) {
            set.add(type.cast(state));
        }
    }
}
