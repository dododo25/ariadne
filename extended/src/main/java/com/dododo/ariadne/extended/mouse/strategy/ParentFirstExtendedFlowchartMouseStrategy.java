package com.dododo.ariadne.extended.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.extended.model.ComplexState;

import java.util.Collection;

public class ParentFirstExtendedFlowchartMouseStrategy extends ParentFirstFlowchartMouseStrategy
        implements ExtendedFlowchartMouseStrategy {

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
