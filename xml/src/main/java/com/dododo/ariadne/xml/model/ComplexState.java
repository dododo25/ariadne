package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ComplexState extends State {

    private final List<State> children;

    public ComplexState() {
        this.children = new CopyOnWriteArrayList<>();
    }

    public int childrenCount() {
        return children.size();
    }

    public State childAt(int index) {
        return children.get(index);
    }

    public Stream<State> childrenStream() {
        return children.stream();
    }

    public void addChild(State state) {
        children.add(state);
        state.addRoot(this);
    }

    public void removeChild(State state) {
        children.remove(state);
        state.removeRoot(this);
    }

    public void replaceChild(State oldState, State newState) {
        int index = children.indexOf(oldState);

        if (index != -1) {
            children.remove(oldState);
            children.add(index, newState);

            oldState.removeRoot(this);
            newState.addRoot(this);
        }
    }

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareByClass(o);
    }
}
