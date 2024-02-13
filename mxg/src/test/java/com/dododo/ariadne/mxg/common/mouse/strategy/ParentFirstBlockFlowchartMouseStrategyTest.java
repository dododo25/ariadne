package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        List<Block> expectedGray = Collections.singletonList(second);
        List<Block> expectedBlack = Collections.singletonList(first);

        first.setNext(second);

        testAccept(expectedGray, expectedBlack, (callback, grayBlocks, blackBlocks) ->
                strategy.acceptChainBlock(first, callback, grayBlocks, blackBlocks));
    }

    @Test
    void testAcceptMenuShouldDoneWell() {
        MenuBlock menu = new MenuBlock(0);
        OptionBlock option = new OptionBlock(1, "test");

        List<Block> expectedGray = Collections.singletonList(option);
        List<Block> expectedBlack = Collections.singletonList(menu);

        menu.addBranch(option);

        testAccept(expectedGray, expectedBlack, (callback, grayBlocks, blackBlocks) ->
                strategy.acceptMenuBlock(menu, callback, grayBlocks, blackBlocks));
    }

    @Test
    void testAcceptSwitchWhenTrueBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expectedGray = Collections.singletonList(state);
        List<Block> expectedBlack = Collections.singletonList(aSwitch);

        aSwitch.setTrueBranch(state);

        testAccept(expectedGray, expectedBlack, (callback, grayBlocks, blackBlocks) ->
                strategy.acceptSwitchBlock(aSwitch, callback, grayBlocks, blackBlocks));
    }

    @Test
    void testAcceptSwitchWhenFalseBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expectedGray = Collections.singletonList(state);
        List<Block> expectedBlack = Collections.singletonList(aSwitch);

        aSwitch.setFalseBranch(state);

        testAccept(expectedGray, expectedBlack, (callback, grayBlocks, blackBlocks) ->
                strategy.acceptSwitchBlock(aSwitch, callback, grayBlocks, blackBlocks));
    }

    @Test
    void testAcceptPointShouldDoneWell() {
        EndBlock endBlock = new EndBlock(0);

        List<Block> expectedGray = Collections.emptyList();
        List<Block> expectedBlack = Collections.singletonList(endBlock);

        testAccept(expectedGray, expectedBlack, (callback, grayBlocks, blackBlocks) ->
                strategy.acceptPoint(endBlock, callback, grayBlocks, blackBlocks));
    }

    private void testAccept(List<Block> expectedGray, List<Block> expectedBlack, TestConsumer consumer) {
        List<Block> grayBlocks = new ArrayList<>();
        List<Block> blackBlocks = new ArrayList<>();

        BlockFlowchartContract callback = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                // test
            }
        };

        consumer.accept(callback, grayBlocks, blackBlocks);

        Assertions.assertIterableEquals(expectedGray, grayBlocks);
        Assertions.assertIterableEquals(expectedBlack, blackBlocks);
    }

    interface TestConsumer {

        void accept(BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks);

    }
}
