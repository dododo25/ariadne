package com.dododo.ariadne.jt.assertions;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.ParentFirstJaxbFlowchartMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class JaxbAssertions {

    private JaxbAssertions() {}

    public static void assertEquals(JaxbState s1, JaxbState s2) {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<JaxbState> states = new ArrayList<>();

        JaxbFlowchartContract c1 = new JaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                states.add(state);
            }
        };

        JaxbFlowchartContract c2 = new JaxbSimpleFlowchartContract() {
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
                }
            }
        };

        if (s1 == s2) {
            return;
        }

        if (s1.compareTo(s2) != 0) {
            throw new AssertionError(String.format("Expected %s, got %s", s1, s2));
        }

        JaxbFlowchartMouse m1 = new ParentFirstJaxbFlowchartMouse();
        JaxbFlowchartMouse m2 = new ParentFirstJaxbFlowchartMouse();

        m1.accept(s1, c1);
        m2.accept(s2, c2);
    }
}
