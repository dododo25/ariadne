package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.AbstractPoint;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

import java.util.Collection;

public interface FlowchartMouseStrategy {

    void acceptChainState(ChainState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);

    void acceptMenu(Menu menu, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);

    void acceptSwitch(Switch aSwitch, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);

    void acceptPoint(AbstractPoint point, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);
}
