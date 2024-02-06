package com.dododo.ariadne.core.mouse.strategy.large;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Collection;
import java.util.Set;

public class ParentFirstLargeTreeFlowchartMouseStrategy extends LargeTreeFlowchartMouseStrategy {

    public ParentFirstLargeTreeFlowchartMouseStrategy(Collection<State> states, int maxDepth) {
        super(states, maxDepth);
    }

    @Override
    public void acceptChainState(ChainState state, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(state);
            visited.remove(state);
            return;
        }

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
            curDepth++;
            current.accept(mouse);
            curDepth--;
        }
    }

    @Override
    public void acceptMenu(Menu menu, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(menu);
            visited.remove(menu);
            return;
        }

        if (visited.contains(menu)) {
            return;
        }

        visited.add(menu);
        callback.accept(menu);

        menu.branchesStream().forEach(option -> {
            curDepth++;
            acceptChainState(option, mouse, callback, visited);
            curDepth--;
        });
    }

    @Override
    public void acceptSwitch(Switch aSwitch, FlowchartMouse mouse, FlowchartContract callback, Set<State> visited) {
        if (curDepth == maxDepth) {
            states.add(aSwitch);
            visited.remove(aSwitch);
            return;
        }

        if (visited.contains(aSwitch)) {
            return;
        }

        visited.add(aSwitch);
        callback.accept(aSwitch);

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
    }
}