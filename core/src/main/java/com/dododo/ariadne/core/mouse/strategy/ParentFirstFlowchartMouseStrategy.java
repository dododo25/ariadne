package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.AbstractPoint;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

import java.util.Collection;

public class ParentFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {
    
    @Override
    public void acceptChainState(ChainState state, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(state);

        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        if (state.getNext() != null) {
            grayStates.add(state.getNext());
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(menu);

        if (blackStates.contains(menu)) {
            return;
        }

        blackStates.add(menu);
        callback.accept(menu);

        menu.branchesStream().forEach(option ->
                acceptChainState(option, callback, grayStates, blackStates));
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(aSwitch);

        if (blackStates.contains(aSwitch)) {
            return;
        }

        blackStates.add(aSwitch);
        callback.accept(aSwitch);

        if (aSwitch.getTrueBranch() != null) {
            grayStates.add(aSwitch.getTrueBranch());
        }

        if (aSwitch.getFalseBranch() != null) {
            grayStates.add(aSwitch.getFalseBranch());
        }
    }

    @Override
    public void acceptPoint(AbstractPoint point, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        grayStates.remove(point);

        if (blackStates.contains(point)) {
            return;
        }

        blackStates.add(point);
        point.accept(callback);
    }
}
