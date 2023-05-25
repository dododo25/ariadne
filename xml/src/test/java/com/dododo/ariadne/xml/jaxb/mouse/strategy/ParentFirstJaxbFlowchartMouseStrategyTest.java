package com.dododo.ariadne.xml.jaxb.mouse.strategy;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

class ParentFirstJaxbFlowchartMouseStrategyTest {

    private static JaxbFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ParentFirstJaxbFlowchartMouseStrategy();
    }

    @Test
    void testAcceptComplexStateShouldDoneWell() {
        JaxbRootState rootState = new JaxbRootState();
        JaxbStatement statement = new JaxbStatement("statement");

        List<JaxbState> expected = Arrays.asList(rootState, statement);

        rootState.addChild(statement);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptComplexState(rootState, callback, mouse));
    }

    private void testAccept(List<JaxbState> expected, BiConsumer<JaxbFlowchartContract, JaxbFlowchartMouse> consumer) {
        List<JaxbState> actual = new ArrayList<>();

        JaxbFlowchartContract callback = new JaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                actual.add(state);
            }
        };

        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
