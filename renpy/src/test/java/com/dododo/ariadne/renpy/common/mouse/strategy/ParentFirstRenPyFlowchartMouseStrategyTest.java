package com.dododo.ariadne.renpy.common.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class ParentFirstRenPyFlowchartMouseStrategyTest {

    private static RenPyFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ParentFirstRenPyFlowchartMouseStrategy();
    }

    @Test
    void testAcceptComplexStateShouldDoneWell() {
        ComplexState first = new ComplexState();
        EndPoint point = new EndPoint();

        List<State> expected = Arrays.asList(first, point);

        first.addChild(point);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptComplexState(first, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<State> expected, BiConsumer<FlowchartContract, FlowchartMouse> consumer) {
        List<State> actual = new ArrayList<>();

        RenPyFlowchartContract callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                actual.add(state);
            }
        };

        FlowchartMouse mouse = new RenPyFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
