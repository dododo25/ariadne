package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.model.ComplexState;

import java.util.Set;

public interface XmlFlowchartMouseStrategy extends FlowchartMouseStrategy {

    void acceptComplexState(ComplexState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited);
}
