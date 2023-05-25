package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstXmlFlowchartMouseStrategyTest {

    private static XmlFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstXmlFlowchartMouseStrategy();
    }

    @Test
    void testAcceptComplexStateShouldDoneWell() {
        ComplexState complexState = new ComplexState();
        EntryState state = new EntryState();

        List<State> expected = Arrays.asList(state, complexState);

        complexState.addChild(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptComplexState(complexState, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<State> expected, BiConsumer<FlowchartContract, FlowchartMouse> consumer) {
        List<State> actual = new ArrayList<>();

        XmlFlowchartContract callback = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                actual.add(state);
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
