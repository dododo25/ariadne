package com.dododo.ariadne.renpy.common.factory;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.large.ParentFirstRenPyLargeTreeFlowchartMouseStrategy;

import java.util.Collection;

public class ParentFirstRenPyLargeTreeFlowchartContractFactory extends RenPyLargeTreeFlowchartContractFactory {

    @Override
    protected LargeTreeFlowchartMouseStrategy prepareStrategy(Collection<State> states, int depth) {
        return new ParentFirstRenPyLargeTreeFlowchartMouseStrategy(states, depth);
    }
}
