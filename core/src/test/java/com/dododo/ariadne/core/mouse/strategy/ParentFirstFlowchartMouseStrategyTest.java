package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
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

class ParentFirstFlowchartMouseStrategyTest {

    private static FlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ParentFirstFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryState first = new EntryState();
        EntryState second = new EntryState();

        List<State> expectedGray = Collections.singletonList(second);
        List<State> expectedBlack = Collections.singletonList(first);

        first.setNext(second);

        testAccept(expectedGray, expectedBlack, (callback, grayStates, blackStates) ->
                strategy.acceptChainState(first, callback, grayStates, blackStates));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        Menu menu = new Menu();
        Option o1 = new Option("o1");
        Option o2 = new Option("o2");

        List<State> expectedGray = Collections.emptyList();
        List<State> expectedBlack = Arrays.asList(menu, o1, o2);

        menu.addBranch(o1);
        menu.addBranch(o2);

        testAccept(expectedGray, expectedBlack, (callback, grayStates, blackStates) ->
                strategy.acceptMenu(menu, callback, grayStates, blackStates));
    }

    @Test
    void testAcceptSwitchShouldDoneWel() {
        Switch aSwitch = new Switch("test");

        Text t1 = new Text("t1");
        Text t2 = new Text("t2");

        List<State> expectedGray = Arrays.asList(t1, t2);
        List<State> expectedBlack = Collections.singletonList(aSwitch);

        aSwitch.setTrueBranch(t1);
        aSwitch.setFalseBranch(t2);

        testAccept(expectedGray, expectedBlack, (callback, grayStates, blackStates) ->
                strategy.acceptSwitch(aSwitch, callback, grayStates, blackStates));
    }

    @Test
    void testAcceptPointShouldDoneWel() {
        EndPoint point = new EndPoint();

        List<State> expectedGray = Collections.emptyList();
        List<State> expectedBlack = Collections.singletonList(point);

        testAccept(expectedGray, expectedBlack, (callback, grayStates, blackStates) ->
                strategy.acceptPoint(point, callback, grayStates, blackStates));
    }

    private void testAccept(Collection<State> expectedGray, Collection<State> expectedBlack, TestConsumer consumer) {
        Collection<State> grayStates = new ArrayList<>();
        Collection<State> blackStates = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
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
