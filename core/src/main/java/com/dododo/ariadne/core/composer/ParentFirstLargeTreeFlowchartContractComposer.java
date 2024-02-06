package com.dododo.ariadne.core.composer;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.large.ParentFirstLargeTreeFlowchartMouseStrategy;

import java.util.Collection;

public class ParentFirstLargeTreeFlowchartContractComposer extends LargeTreeFlowchartContractComposer {

    @Override
    protected LargeTreeFlowchartMouseStrategy prepareStrategy(Collection<State> states, int depth) {
        return new ParentFirstLargeTreeFlowchartMouseStrategy(states, depth);
    }
}
