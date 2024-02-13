package com.dododo.ariadne.renpy.jaxb.test.assertions;

import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.SimpleRenPyJaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class JaxbAssertions {

    private JaxbAssertions() {}

    public static void assertEquals(JaxbState s1, JaxbState s2) {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<JaxbState> states = new ArrayList<>();

        RenPyJaxbFlowchartContract c1 = new SimpleRenPyJaxbFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                states.add(state);
            }
        };

        RenPyJaxbFlowchartContract c2 = new SimpleRenPyJaxbFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                if (stopProcessingRef.get()) {
                    return;
                }

                if (states.isEmpty()) {
                    throw new AssertionError(String.format("Unexpected state %s", state));
                }

                JaxbState s = states.remove(0);

                if (s.compareTo(state) != 0) {
                    throw new AssertionError(String.format("Expected %s, got %s", s, state));
                } else if (s == state) {
                    stopProcessingRef.set(true);
                }
            }
        };

        if (s1 == s2) {
            return;
        }

        if (s1.compareTo(s2) != 0) {
            throw new AssertionError(String.format("Expected %s, got %s", s1, s2));
        }

        ParentFirstRenPyJaxbFlowchartMouse m1 = new ParentFirstRenPyJaxbFlowchartMouse();
        ParentFirstRenPyJaxbFlowchartMouse m2 = new ParentFirstRenPyJaxbFlowchartMouse();

        m1.accept(s1, c1);
        m2.accept(s2, c2);
    }
}
