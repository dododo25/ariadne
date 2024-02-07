package com.dododo.ariadne.renpy.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.model.ComplexState;

import java.util.Collection;

public interface RenPyFlowchartMouseStrategy extends FlowchartMouseStrategy {

    void acceptComplexState(ComplexState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);
}
