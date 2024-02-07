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
import java.util.List;

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

        List<State> expectedGray = Collections.singletonList(first);
        List<State> expectedBlack = Collections.singletonList(second);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        first.setNext(second);

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        second.accept(strategy, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        EntryState first = new EntryState();
        Menu second = new Menu();

        List<State> expectedGray = Collections.singletonList(first);
        List<State> expectedBlack = Collections.singletonList(second);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        first.setNext(second);

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        second.accept(strategy, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    @Test
    void testAcceptSwitchShouldDoneWel() {
        Switch aSwitch = new Switch("test");

        Text t1 = new Text("t1");
        Text t2 = new Text("t2");

        List<State> expectedGray = Arrays.asList(aSwitch, aSwitch);
        List<State> expectedBlack = Arrays.asList(t1, t2);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        aSwitch.setTrueBranch(t1);
        aSwitch.setFalseBranch(t2);

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        t1.accept(strategy, callback, grayStates, blackStates);
        t2.accept(strategy, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }

    @Test
    void testAcceptPointShouldDoneWell() {
        EndPoint point = new EndPoint();

        List<State> expectedGray = Collections.emptyList();
        List<State> expectedBlack = Collections.singletonList(point);

        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // test
            }
        };

        point.accept(strategy, callback, grayStates, blackStates);

        Assertions.assertIterableEquals(expectedGray, grayStates);
        Assertions.assertIterableEquals(expectedBlack, blackStates);
    }
}
