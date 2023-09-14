package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
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
    public void accept(CycleMarker marker) {
        strategy.acceptChainState(marker, this, callback, visited);
    }

    @Override
    public void accept(CycleEntryState state) {
        strategy.acceptChainState(state, this, callback, visited);
    }

    @Override
    public void accept(Text text) {
        strategy.acceptChainState(text, this, callback, visited);
    }

    @Override
    public void accept(Reply reply) {
        strategy.acceptChainState(reply, this, callback, visited);
    }

    @Override
    public void accept(Menu menu) {
        strategy.acceptMenu(menu, this, callback, visited);
    }

    @Override
    public void accept(Option option) {
        strategy.acceptChainState(option, this, callback, visited);
    }

    @Override
    public void accept(ConditionalOption option) {
        strategy.acceptChainState(option, this, callback, visited);
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
