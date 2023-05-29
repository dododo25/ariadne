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
        if (!visited.contains(state)) {
            visited.add(state);

            LinkedList<ChainState> states = prepareChain(state);

            if (!states.isEmpty()) {
                if (states.getFirst().getNext() != null) {
                    states.getFirst().getNext().accept(mouse);
                }

                for (State s : states) {
                    if (visited.contains(s)) {
                        break;
                    }

                    visited.add(s);
                    s.accept(callback);
                }
            } else if (state.getNext() != null) {
                state.getNext().accept(mouse);
            }

            state.accept(callback);
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (!visited.contains(menu)) {
            visited.add(menu);

            menu.branchesStream()
                    .forEach(mouse::accept);

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

    private static LinkedList<ChainState> prepareChain(ChainState state) {
        LinkedList<ChainState> result = new LinkedList<>();
        State s = state.getNext();

        while (s instanceof ChainState) {
            if (result.contains(s)) {
                break;
            }

            result.addFirst((ChainState) s);
            s = ((ChainState) s).getNext();
        }

        return result;
    }
}
