package com.dododo.ariadne.xml.common.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.mouse.strategy.XmlFlowchartMouseStrategy;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ComplexState extends State {

    private final List<State> states;

    public ComplexState() {
        this.states = new CopyOnWriteArrayList<>();
    }

    public int childrenCount() {
        return states.size();
    }

    public State childAt(int index) {
        return states.get(index);
    }

    public Stream<State> childrenStream() {
        return states.stream();
    }

    public void addChild(State state) {
        states.add(state);

        if (state != null) {
            state.addRoot(this);
        }
    }

    public void replaceChild(State oldState, State newState) {
        int index = states.indexOf(oldState);

        if (index != -1) {
            states.remove(oldState);
            states.add(index, newState);

            oldState.removeRoot(this);
            newState.addRoot(this);
        }
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }

    @Override
    public final void accept(FlowchartMouseStrategy strategy, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        ((XmlFlowchartMouseStrategy) strategy).acceptComplexState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(State o) {
        return o instanceof ComplexState ? 0 : 1;
    }
}
