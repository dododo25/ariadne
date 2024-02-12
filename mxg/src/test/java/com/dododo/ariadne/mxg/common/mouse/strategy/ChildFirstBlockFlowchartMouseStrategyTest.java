package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.mouse.strategy.BlockFlowchartMouseStrategy;
import com.dododo.ariadne.mxg.common.mouse.strategy.ChildFirstBlockFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.BiConsumer;

class ChildFirstBlockFlowchartMouseStrategyTest {

    private static BlockFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstBlockFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryBlock first = new EntryBlock(0);
        EntryBlock second = new EntryBlock(1);

        Collection<Block> expected = Collections.singletonList(second);

        first.setNext(second);

        testAccept(expected, (callback, blackBlocks) ->
                strategy.acceptChainBlock(second, callback, new HashSet<>(), blackBlocks));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        EntryBlock block = new EntryBlock(0);
        MenuBlock menu = new MenuBlock(1);

        Collection<Block> expected = Collections.singletonList(menu);

        block.setNext(menu);

        testAccept(expected, (callback, blackBlocks) ->
                strategy.acceptMenuBlock(menu, callback, new HashSet<>(), blackBlocks));
    }

    @Test
    void testAcceptSwitchShouldDoneWell() {
        EntryBlock block = new EntryBlock(0);
        SwitchBlock aSwitch = new SwitchBlock(1, "test");

        Collection<Block> expected = Collections.singletonList(aSwitch);

        block.setNext(aSwitch);

        testAccept(expected, (callback, blackBlocks) ->
                strategy.acceptSwitchBlock(aSwitch, callback, new HashSet<>(), blackBlocks));
    }

    @Test
    void testAcceptPointShouldDoneWell() {
        EntryBlock block = new EntryBlock(0);
        EndBlock endBlock = new EndBlock(1);

        Collection<Block> expected = Collections.singletonList(endBlock);

        block.setNext(endBlock);

        testAccept(expected, (callback, blackBlocks) ->
                strategy.acceptPoint(endBlock, callback, new HashSet<>(), blackBlocks));
    }

    private void testAccept(Collection<Block> expected, BiConsumer<BlockFlowchartContract, Collection<Block>> consumer) {
        Collection<Block> blocks = new ArrayList<>();

        BlockFlowchartContract callback = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                // test
            }
        };

        consumer.accept(callback, blocks);
        Assertions.assertIterableEquals(expected, blocks);
    }
}
