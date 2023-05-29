package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.LinkedList;
import java.util.Set;

public class ParentFirstFlowchartMouseStrategy implements FlowchartMouseStrategy {

    @Override
    public void acceptChainState(ChainState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(state)) {
            LinkedList<ChainState> states = prepareChain(state);

            for (State s : states) {
                if (visited.contains(s)) {
                    break;
                }

                visited.add(s);
                s.accept(callback);
            }

            if (states.getLast().getNext() != null) {
                states.getLast().getNext().accept(mouse);
            }
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(menu)) {
            visited.add(menu);
            callback.accept(menu);

            menu.branchesStream()
                    .forEach(mouse::accept);
        }
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(aSwitch)) {
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

    private static LinkedList<ChainState> prepareChain(ChainState state) {
        LinkedList<ChainState> result = new LinkedList<>();
        State s = state;

        while (s instanceof ChainState) {
            if (result.contains(s)) {
                break;
            }

            result.addLast((ChainState) s);
            s = ((ChainState) s).getNext();
        }

        return result;
    }
}
