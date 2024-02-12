package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

class ChildFirstFlowchartMouseStrategyTest {

    private static FlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryState first = new EntryState();
        EntryState second = new EntryState();

        Collection<State> expected = Collections.singletonList(second);
        Collection<State> states = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        first.setNext(second);
        second.accept(strategy, callback, new HashSet<>(), states);

        Assertions.assertIterableEquals(expected, states);
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        EntryState first = new EntryState();
        Menu second = new Menu();

        Collection<State> expected = Collections.singletonList(second);
        Collection<State> states = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        first.setNext(second);
        second.accept(strategy, callback, new HashSet<>(), states);

        Assertions.assertIterableEquals(expected, states);
    }

    @Test
    void testAcceptSwitchShouldDoneWell() {
        Switch aSwitch = new Switch("test");

        Text t1 = new Text("t1");
        Text t2 = new Text("t2");

        Collection<State> expected = Arrays.asList(t1, t2);
        Collection<State> states = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        aSwitch.setTrueBranch(t1);
        aSwitch.setFalseBranch(t2);

        t1.accept(strategy, callback, new HashSet<>(), states);
        t2.accept(strategy, callback, new HashSet<>(), states);

        Assertions.assertIterableEquals(expected, states);
    }

    @Test
    void testAcceptPointShouldDoneWell() {
        EndPoint point = new EndPoint();

        Collection<State> expected = Collections.singletonList(point);
        Collection<State> states = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        point.accept(strategy, callback, new HashSet<>(), states);

        Assertions.assertIterableEquals(expected, states);
    }
}
