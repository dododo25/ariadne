package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.model.ComplexState;

import java.util.Collection;

public final class ChildFirstXmlFlowchartMouseStrategy extends ChildFirstFlowchartMouseStrategy
        implements XmlFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(ComplexState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        acceptRoots(state, grayStates);
    }
}
