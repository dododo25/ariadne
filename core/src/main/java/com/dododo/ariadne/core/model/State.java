package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.util.comparator.NullableStringComparator;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

public abstract class State implements Comparable<State> {

    private final Set<State> roots;

    protected final Comparator<String> comparator;

    protected State() {
        this.roots = new CopyOnWriteArraySet<>();
        this.comparator = new NullableStringComparator();
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

    protected int compareByClass(State state) {
        if (!(this.getClass().isInstance(state))) {
            return 1;
        }

        return 0;
    }

    protected int compareBySingleValue(State state, Function<State, String> function) {
        if (!(state.getClass().isInstance(this))) {
            return 1;
        }

        String v0 = function.apply(state);
        String v1 = function.apply(this);

        return comparator.compare(v0, v1);
    }

    protected int compareByValuesPair(State state, Function<State, String> f1, Function<State, String> f2) {
        if (this.getClass() != state.getClass()) {
            return 1;
        }

        int res = comparator.compare(f1.apply(state), f1.apply(this));

        if (res == 0) {
            return comparator.compare(f2.apply(state), f2.apply(this));
        }

        return res;
    }
}
