package com.dododo.ariadne.core.mouse.strategy.large;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;

import java.util.Collection;

public abstract class LargeTreeFlowchartMouseStrategy implements FlowchartMouseStrategy {

    public static final int DEFAULT_DEPTH = 16;

    protected final Collection<State> states;

    protected final int maxDepth;

    protected int curDepth;

    protected LargeTreeFlowchartMouseStrategy(Collection<State> states, int maxDepth) {
        this.states = states;
        this.maxDepth = maxDepth;
        this.curDepth = 0;
    }
}