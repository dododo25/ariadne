package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;

import java.util.Set;

public interface JaxbFlowchartMouseStrategy {

    void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback,
                            JaxbFlowchartMouse mouse, Set<JaxbState> visited);
}
