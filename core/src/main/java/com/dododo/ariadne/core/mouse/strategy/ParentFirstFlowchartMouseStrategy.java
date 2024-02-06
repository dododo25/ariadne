package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Set;

public class ParentFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {

    @Override
    public void acceptChainState(ChainState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        State current = state;

        while (current instanceof ChainState) {
            if (visited.contains(current)) {
                break;
            }

            visited.add(current);
            current.accept(callback);

            current = ((ChainState) current).getNext();
        }

        if (current != null && !visited.contains(current)) {
            current.accept(mouse);
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (visited.contains(menu)) {
            return;
        }

        visited.add(menu);
        callback.accept(menu);

        menu.branchesStream().forEach(option ->
                acceptChainState(option, mouse, callback, visited));
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (visited.contains(aSwitch)) {
            return;
        }

        visited.add(aSwitch);
        callback.accept(aSwitch);

        if (aSwitch.getTrueBranch() != null) {
            aSwitch.getTrueBranch().accept(mouse);
        }

        if (aSwitch.getFalseBranch() != null) {
            aSwitch.getFalseBranch().accept(mouse);
        }
    }
}
