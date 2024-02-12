package com.dododo.ariadne.extended.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstExtendedFlowchartMouseStrategyTest {

    private static ExtendedFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstExtendedFlowchartMouseStrategy();
    }

    @Test
    void testAcceptPointShouldDoneWell() {
        ComplexState first = new ComplexState();
        EndPoint point = new EndPoint();

        List<State> expected = Collections.singletonList(point);

        first.addChild(point);

        testAccept(expected, (callback, blackStates) ->
                strategy.acceptPoint(point, callback, new HashSet<>(), blackStates));
    }

    private void testAccept(Collection<State> expected, BiConsumer<FlowchartContract, Collection<State>> consumer) {
        Collection<State> states = new ArrayList<>();

        FlowchartContract callback = new ExtendedSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        consumer.accept(callback, states);
        Assertions.assertIterableEquals(expected, states);
    }
}
