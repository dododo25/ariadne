package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.LinkedList;
import java.util.Set;

public class ChildFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {

    @Override
    public void acceptChainState(ChainState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        LinkedList<ChainState> states = new LinkedList<>();

        State current = state;

        while (current instanceof ChainState) {
            if (states.contains(current)) {
                break;
            }

            states.addFirst((ChainState) current);
            current = ((ChainState) current).getNext();
        }

        if (current != null && !visited.contains(current)) {
            current.accept(mouse);
        }

        for (State s : states) {
            visited.add(s);
            s.accept(callback);
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(menu)) {
            visited.add(menu);

            menu.branchesStream().forEach(option ->
                    acceptChainState(option, mouse, callback, visited));

            callback.accept(menu);
        }
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(aSwitch)) {
            visited.add(aSwitch);

            if (aSwitch.getTrueBranch() != null) {
                aSwitch.getTrueBranch().accept(mouse);
            }

            if (aSwitch.getFalseBranch() != null) {
                aSwitch.getFalseBranch().accept(mouse);
            }

            callback.accept(aSwitch);
        }
    }
}
