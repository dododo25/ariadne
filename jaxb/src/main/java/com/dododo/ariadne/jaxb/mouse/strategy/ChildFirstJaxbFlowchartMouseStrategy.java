package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.Collection;

public final class ChildFirstJaxbFlowchartMouseStrategy implements JaxbFlowchartMouseStrategy {

    @Override
    public void acceptSingleState(JaxbState state, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        grayStates.remove(state);

        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);

        state.accept(callback);
        acceptRoots(state, grayStates);
    }

    @Override
    public void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        grayStates.remove(complexState);

        if (blackStates.contains(complexState)) {
            return;
        }

        blackStates.add(complexState);

        complexState.accept(callback);
        acceptRoots(complexState, grayStates);
    }

    private void acceptRoots(JaxbState state, Collection<JaxbState> grayStates) {
        JaxbState root = state.getRoot();

        if (root != null) {
            grayStates.add(root);
        }
    }
}
