package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

class ChildFirstParentFirstJaxbFlowchartMouseStrategyTest {

    private static JaxbFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstJaxbFlowchartMouseStrategy();
    }

    @Test
    void testAcceptComplexStateShouldDoneWell() {
        JaxbRootState rootState = new JaxbRootState();
        JaxbText text = new JaxbText("text");

        Collection<JaxbState> expected = Collections.singletonList(text);
        Collection<JaxbState> states = new ArrayList<>();

        JaxbFlowchartContract callback = new JaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                // test
            }
        };

        rootState.addChild(text);
        text.accept(strategy, callback, new HashSet<>(), states);

        Assertions.assertIterableEquals(expected, states);
    }
}
