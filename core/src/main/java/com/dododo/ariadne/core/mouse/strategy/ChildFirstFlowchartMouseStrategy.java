package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.AbstractPoint;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

import java.util.Arrays;
import java.util.Collection;

public class ChildFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {

    @Override
    public void acceptChainState(ChainState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        acceptRoots(state, grayStates);
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(menu)) {
            return;
        }

        blackStates.add(menu);
        callback.accept(menu);

        acceptRoots(menu, grayStates);
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(aSwitch)) {
            return;
        }

        blackStates.add(aSwitch);
        callback.accept(aSwitch);

        acceptRoots(aSwitch, grayStates);
    }

    @Override
    public void acceptPoint(AbstractPoint point, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        if (blackStates.contains(point)) {
            return;
        }

        blackStates.add(point);
        point.accept(callback);

        acceptRoots(point, grayStates);
    }

    protected void acceptRoots(State state, Collection<State> grayStates) {
        grayStates.addAll(Arrays.asList(state.getRoots()));
    }
}
