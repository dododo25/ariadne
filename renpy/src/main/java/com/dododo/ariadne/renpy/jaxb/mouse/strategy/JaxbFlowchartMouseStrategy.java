package com.dododo.ariadne.renpy.jaxb.mouse.strategy;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;

public interface JaxbFlowchartMouseStrategy {

    void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, JaxbFlowchartMouse mouse);
}
