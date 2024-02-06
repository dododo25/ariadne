package com.dododo.ariadne.core.comparator;

import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class StateComparator implements Comparator<State> {

    private final FlowchartContractComposer composer;

    public StateComparator(FlowchartContractComposer composer) {
        this.composer = composer;
    }

    @Override
    public int compare(State s1, State s2) {
        AtomicInteger result = new AtomicInteger(0);

        List<State> states = new ArrayList<>();

        Consumer<State> c1 = states::add;

        Consumer<State> c2 = state -> {
            if (result.get() != 0) {
                return;
            }

            if (states.isEmpty()) {
                result.set(-1);
            } else {
                State s = states.remove(0);

                if (s.compareTo(state) != 0) {
                    result.set(-1);
                } else if (s == state) {
                    result.set(2);
                }
            }
        };

        if (s1 == s2) {
            return 0;
        }

        if (s1.compareTo(s2) != 0) {
            return -1;
        }

        composer.process(s1, c1);
        composer.process(s2, c2);

        return result.get() % 2;
    }
}
