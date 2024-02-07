package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.model.ComplexState;

import java.util.Collection;

public interface XmlFlowchartMouseStrategy extends FlowchartMouseStrategy {

    void acceptComplexState(ComplexState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);
}
