package com.dododo.ariadne.core.mouse.strategy;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

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
        EndPoint point = new EndPoint();

        List<State> expected = Arrays.asList(point, second, first);

        first.setNext(second);
        second.setNext(point);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptChainState(first, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        Menu menu = new Menu();
        Option option = new Option("test");

        List<State> expected = Arrays.asList(option, menu);

        menu.addBranch(option);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptMenu(menu, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenTrueBranchExistsShouldDoneWell() {
        Switch aSwitch = new Switch("test");
        EntryState state = new EntryState();

        List<State> expected = Arrays.asList(state, aSwitch);

        aSwitch.setTrueBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitch(aSwitch, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenFalseBranchExistsShouldDoneWell() {
        Switch aSwitch = new Switch("test");
        EntryState state = new EntryState();

        List<State> expected = Arrays.asList(state, aSwitch);

        aSwitch.setFalseBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitch(aSwitch, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<State> expected, BiConsumer<FlowchartContract, FlowchartMouse> consumer) {
        List<State> actual = new ArrayList<>();

        FlowchartContract callback = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                actual.add(state);
            }
        };

        FlowchartMouse mouse = new FlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
