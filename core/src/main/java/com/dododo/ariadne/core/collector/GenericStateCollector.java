package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.State;

import java.util.Set;

public class GenericStateCollector<S extends State> extends StateCollector<S> {

    private final Class<S> type;

    public GenericStateCollector(FlowchartMouseFactory factory, Class<S> type) {
        super(factory);
        this.type = type;
    }

    @Override
    public void accept(Set<S> set, State state) {
        if (type.isAssignableFrom(state.getClass())) {
            set.add(type.cast(state));
        }
    }
}
