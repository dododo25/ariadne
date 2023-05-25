package com.dododo.ariadne.xml.jaxb.mouse.strategy;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;

public final class ChildFirstJaxbFlowchartMouseStrategy implements JaxbFlowchartMouseStrategy {

    @Override
    public void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, JaxbFlowchartMouse mouse) {
        complexState.childrenStream()
                .forEach(child -> child.accept(mouse));

        complexState.accept(callback);
    }
}
