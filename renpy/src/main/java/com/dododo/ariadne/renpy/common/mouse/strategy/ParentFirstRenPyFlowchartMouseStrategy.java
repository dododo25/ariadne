package com.dododo.ariadne.renpy.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.model.ComplexState;

import java.util.Set;

public class ParentFirstRenPyFlowchartMouseStrategy extends ParentFirstFlowchartMouseStrategy
        implements RenPyFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(ComplexState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (visited.contains(state)) {
            return;
        }

        visited.add(state);
        state.accept(callback);

        state.childrenStream()
                .forEach(s -> s.accept(mouse));
    }
}
