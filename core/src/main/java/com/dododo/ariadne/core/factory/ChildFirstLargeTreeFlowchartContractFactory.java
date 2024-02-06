package com.dododo.ariadne.core.factory;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.large.ChildFirstLargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;

import java.util.Collection;

public class ChildFirstLargeTreeFlowchartContractFactory extends LargeTreeFlowchartContractFactory {

    @Override
    protected LargeTreeFlowchartMouseStrategy prepareStrategy(Collection<State> states, int depth) {
        return new ChildFirstLargeTreeFlowchartMouseStrategy(states, depth);
    }
}
