package com.dododo.ariadne.thread.mouse.strategy;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.contract.ThreadSimpleFlowchartContract;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.EndBlock;
import com.dododo.ariadne.thread.model.EntryBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.OptionBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class ThreadChildFirstFlowchartMouseStrategyTest {

    private static ThreadFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ThreadChildrenFirstFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryBlock first = new EntryBlock(0);
        EntryBlock second = new EntryBlock(1);
        EndBlock point = new EndBlock(2);

        List<Block> expected = Arrays.asList(point, second, first);

        first.setNext(second);
        second.setNext(point);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptChainBlock(first, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        MenuBlock menu = new MenuBlock(0);
        OptionBlock option = new OptionBlock(1, "test");

        List<Block> expected = Arrays.asList(option, menu);

        menu.addBranch(option);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptMenuBlock(menu, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenTrueBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(state, aSwitch);

        aSwitch.setTrueBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenFalseBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(state, aSwitch);

        aSwitch.setFalseBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<Block> expected, BiConsumer<ThreadFlowchartContract, ThreadFlowchartMouse> consumer) {
        List<Block> actual = new ArrayList<>();

        ThreadFlowchartContract callback = new ThreadSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                actual.add(block);
            }
        };

        ThreadFlowchartMouse mouse = new ThreadFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
