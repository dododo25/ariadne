package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.model.ComplexState;

import java.util.Set;

public final class ChildFirstXmlFlowchartMouseStrategy extends ChildFirstFlowchartMouseStrategy
        implements XmlFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(ComplexState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(state)) {
            visited.add(state);

            state.childrenStream()
                    .forEach(child -> child.accept(mouse));

            state.accept(callback);
        }
    }
}
