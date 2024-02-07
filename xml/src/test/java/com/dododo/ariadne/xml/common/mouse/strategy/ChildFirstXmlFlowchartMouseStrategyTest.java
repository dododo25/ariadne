package com.dododo.ariadne.xml.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.model.ComplexState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class ChildFirstXmlFlowchartMouseStrategyTest {

    private static XmlFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstXmlFlowchartMouseStrategy();
    }

    @Test
    void testAcceptComplexStateShouldDoneWell() {
        ComplexState first = new ComplexState();
        EndPoint point = new EndPoint();

        List<State> expectedGray = Collections.singletonList(first);
        List<State> expectedBlack = Collections.singletonList(point);

        first.addChild(point);

        testAccept(expectedGray, expectedBlack, (callback, grayStates, blackStates) ->
                strategy.acceptPoint(point, callback, grayStates, blackStates));
    }

    private void testAccept(Collection<State> expectedGray, Collection<State> expectedBlack, TestConsumer consumer) {
        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        FlowchartContract callback = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        consumer.accept(callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    interface TestConsumer {

        void accept(FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates);

    }
}
