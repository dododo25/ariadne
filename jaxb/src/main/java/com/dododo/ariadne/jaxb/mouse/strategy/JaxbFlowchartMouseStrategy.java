package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.Collection;

public interface JaxbFlowchartMouseStrategy {

    void acceptSingleState(JaxbState state, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates);

    void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates);
}
