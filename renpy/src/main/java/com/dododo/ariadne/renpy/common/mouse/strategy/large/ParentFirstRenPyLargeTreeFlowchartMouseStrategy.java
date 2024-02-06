package com.dododo.ariadne.renpy.common.mouse.strategy.large;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.large.ParentFirstLargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.model.ComplexState;

import java.util.Collection;
import java.util.Set;

public class ParentFirstRenPyLargeTreeFlowchartMouseStrategy extends ParentFirstLargeTreeFlowchartMouseStrategy
        implements RenPyLargeTreeFlowchartMouseStrategy {

    public ParentFirstRenPyLargeTreeFlowchartMouseStrategy(Collection<State> states, int maxDepth) {
        super(states, maxDepth);
    }

    @Override
    public void acceptComplexState(ComplexState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(state);
            visited.remove(state);
            return;
        }

        if (visited.contains(state)) {
            return;
        }

        visited.add(state);
        state.accept(callback);

        state.childrenStream().forEach(s -> {
            curDepth++;
            s.accept(mouse);
            curDepth--;
        });
    }
}