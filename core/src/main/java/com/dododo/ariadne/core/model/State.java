package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class State implements Comparable<State> {

    private final Set<State> roots;

    protected State() {
        this.roots = new CopyOnWriteArraySet<>();
    }

    public State[] getRoots() {
        return roots.toArray(new State[0]);
    }

    public void addRoot(State state) {
        roots.add(state);
    }

    public void removeRoot(State state) {
        roots.remove(state);
    }

    public abstract void accept(FlowchartContract contract);
}
