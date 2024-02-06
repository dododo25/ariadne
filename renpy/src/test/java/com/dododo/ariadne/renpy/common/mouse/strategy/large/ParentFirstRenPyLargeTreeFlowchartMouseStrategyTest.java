package com.dododo.ariadne.renpy.common.mouse.strategy.large;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ParentFirstRenPyLargeTreeFlowchartMouseStrategyTest {

    @Test
    void testAcceptTreeShouldDoneWell() {
        Switch s1 = new Switch("s1");
        Switch s2 = new Switch("s2");
        Switch s3 = new Switch("s3");
        Switch s4 = new Switch("s4");

        List<State> expected = Arrays.asList(s1, s2, s3, s4);

        s1.setTrueBranch(s2);
        s2.setTrueBranch(s3);
        s3.setTrueBranch(s4);

        testAcceptTreeShouldDoneWell(expected, s1);
    }

    private void testAcceptTreeShouldDoneWell(Collection<State> expected, State rootState) {
        List<State> states = new ArrayList<>();
        List<State> collected = new ArrayList<>();

        LargeTreeFlowchartMouseStrategy strategy = new ParentFirstRenPyLargeTreeFlowchartMouseStrategy(states, 2);

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                collected.add(state);
            }
        };

        FlowchartContract contract = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                FlowchartMouse mouse = new FlowchartMouse(callback, strategy);

                state.accept(mouse);

                while (!states.isEmpty()) {
                    states.stream().findAny().ifPresent(nextState -> {
                        states.remove(nextState);
                        nextState.accept(mouse);
                    });
                }
            }
        };

        rootState.accept(contract);

        Assertions.assertEquals(expected, collected);
    }
}