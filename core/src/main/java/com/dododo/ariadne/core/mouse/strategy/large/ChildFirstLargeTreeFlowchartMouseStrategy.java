package com.dododo.ariadne.core.mouse.strategy.large;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

public class ChildFirstLargeTreeFlowchartMouseStrategy extends LargeTreeFlowchartMouseStrategy {

    public ChildFirstLargeTreeFlowchartMouseStrategy(Collection<State> states, int maxDepth) {
        super(states, maxDepth);
    }

    @Override
    public void acceptChainState(ChainState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(state);
            visited.remove(state);
            return;
        }

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
            curDepth++;
            current.accept(mouse);
            curDepth--;
        }

        for (State s : states) {
            visited.add(s);
            s.accept(callback);
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(menu);
            visited.remove(menu);
            return;
        }

        if (!visited.contains(menu)) {
            visited.add(menu);

            menu.branchesStream().forEach(option -> {
                curDepth++;
                acceptChainState(option, mouse, callback, visited);
                curDepth--;
            });

            callback.accept(menu);
        }
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(aSwitch);
            visited.remove(aSwitch);
            return;
        }

        if (!visited.contains(aSwitch)) {
            visited.add(aSwitch);

            if (aSwitch.getTrueBranch() != null) {
                curDepth++;
                aSwitch.getTrueBranch().accept(mouse);
                curDepth--;
            }

            if (aSwitch.getFalseBranch() != null) {
                curDepth++;
                aSwitch.getFalseBranch().accept(mouse);
                curDepth--;
            }

            callback.accept(aSwitch);
        }
    }
}