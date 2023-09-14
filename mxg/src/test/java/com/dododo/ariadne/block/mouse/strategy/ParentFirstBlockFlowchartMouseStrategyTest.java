package com.dododo.ariadne.block.mouse.strategy;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.mouse.BlockFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class ParentFirstBlockFlowchartMouseStrategyTest {

    private static BlockFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ParentFirstBlockFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryBlock first = new EntryBlock(0);
        EntryBlock second = new EntryBlock(1);
        EndBlock point = new EndBlock(2);

        List<Block> expected = Arrays.asList(first, second, point);

        first.setNext(second);
        second.setNext(point);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptChainBlock(first, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        MenuBlock menu = new MenuBlock(0);
        OptionBlock option = new OptionBlock(1, "test");

        List<Block> expected = Arrays.asList(menu, option);

        menu.addBranch(option);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptMenuBlock(menu, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenTrueBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(aSwitch, state);

        aSwitch.setTrueBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenFalseBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(aSwitch, state);

        aSwitch.setFalseBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<Block> expected, BiConsumer<BlockFlowchartContract, BlockFlowchartMouse> consumer) {
        List<Block> actual = new ArrayList<>();

        BlockFlowchartContract callback = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                actual.add(block);
            }
        };

        BlockFlowchartMouse mouse = new BlockFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
