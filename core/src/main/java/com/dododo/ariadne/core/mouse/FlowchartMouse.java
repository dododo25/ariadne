package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;

import java.util.HashSet;
import java.util.Set;

public class FlowchartMouse implements FlowchartContract {

    protected final FlowchartContract callback;

    protected final FlowchartMouseStrategy strategy;

    protected final Set<State> visited;

    public FlowchartMouse(FlowchartContract callback, FlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
        this.visited = new HashSet<>();
    }

    @Override
    public void accept(EntryState state) {
        strategy.acceptChainState(state, this, callback, visited);
    }

    @Override
    public void accept(Statement statement) {
        strategy.acceptChainState(statement, this, callback, visited);
    }

    @Override
    public void accept(Switch aSwitch) {
        strategy.acceptSwitch(aSwitch, this, callback, visited);
    }

    @Override
    public void accept(EndPoint point) {
        acceptEndState(point);
    }

    protected void acceptEndState(State state) {
        if (!visited.contains(state)) {
            visited.add(state);
            state.accept(callback);
        }
    }
}
