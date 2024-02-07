package com.dododo.ariadne.renpy.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.model.ComplexState;

import java.util.Collection;

public class ParentFirstRenPyFlowchartMouseStrategy extends ParentFirstFlowchartMouseStrategy
        implements RenPyFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(ComplexState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        state.childrenStream()
                .forEach(grayStates::add);
    }
}
