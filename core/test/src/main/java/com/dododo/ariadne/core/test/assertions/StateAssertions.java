package com.dododo.ariadne.core.test.assertions;

import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class StateAssertions {

    private StateAssertions() {}

    public static void assertEquals(State s1, State s2, FlowchartMouse mouse) throws AssertionError {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<State> states = new ArrayList<>();

        Consumer<State> c1 = states::add;
        Consumer<State> c2 = state -> {
            if (stopProcessingRef.get()) {
                return;
            }

            if (states.isEmpty()) {
                throw new AssertionError(String.format("Unexpected state %s", state));
            }

            State s = states.remove(0);

            if (s.compareTo(state) != 0) {
                throw new AssertionError(String.format("Expected %s, got %s", s, state));
            } else if (s == state) {
                stopProcessingRef.set(true);
            }
        };

        if (s1 == s2) {
            return;
        }

        if (s1.compareTo(s2) != 0) {
            throw new AssertionError(String.format("Expected %s, got %s", s1, s2));
        }

        mouse.accept(s1, c1);
        mouse.accept(s2, c2);
    }
}
