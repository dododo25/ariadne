package com.dododo.ariadne.xml.jaxb.mouse.strategy;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;

public interface JaxbFlowchartMouseStrategy {

    void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, JaxbFlowchartMouse mouse);
}
