package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

class ChildFirstBlockFlowchartMouseStrategyTest {

    private static BlockFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new ChildFirstBlockFlowchartMouseStrategy();
    }

    @Test
    void testAcceptStateShouldDoneWell() {
        testAccept(new EntryBlock(0), new EntryBlock(1), ChainBlock::setNext,
                (block, callback, grayStates, blackStates) -> strategy.acceptChainBlock(block, callback, grayStates, blackStates));
        testAccept(new TextBlock(0, "test"), new EntryBlock(1), ChainBlock::setNext,
                (block, callback, grayStates, blackStates) -> strategy.acceptChainBlock(block, callback, grayStates, blackStates));
        testAccept(new ReplyBlock(0, null, "test"), new EntryBlock(1), ChainBlock::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainBlock(state, callback, grayStates, blackStates));
        testAccept(new OptionBlock(0, "test"), new EntryBlock(1), ChainBlock::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainBlock(state, callback, grayStates, blackStates));
        testAccept(new ConditionalOptionBlock(0, "test", "test"), new EntryBlock(1), ChainBlock::setNext,
                (state, callback, grayStates, blackStates) -> strategy.acceptChainBlock(state, callback, grayStates, blackStates));

        testAccept(new MenuBlock(0), new OptionBlock(1, "test"), MenuBlock::addBranch,
                (block, callback, grayStates, blackStates) -> strategy.acceptChainBlock(block, callback, grayStates, blackStates));

        testAccept(new SwitchBlock(0, "test"), new EntryBlock(1), SwitchBlock::setTrueBranch,
                (block, callback, grayStates, blackStates) -> strategy.acceptChainBlock(block, callback, grayStates, blackStates));
        testAccept(new SwitchBlock(0, "test"), new EntryBlock(1), SwitchBlock::setFalseBranch,
                (block, callback, grayStates, blackStates) -> strategy.acceptChainBlock(block, callback, grayStates, blackStates));

        testAccept(new EntryBlock(0), new MenuBlock(1), ChainBlock::setNext,
                (block, callback, grayStates, blackStates) -> strategy.acceptMenuBlock(block, callback, grayStates, blackStates));
        testAccept(new EntryBlock(0), new SwitchBlock(1, "test"), ChainBlock::setNext,
                (block, callback, grayStates, blackStates) -> strategy.acceptSwitchBlock(block, callback, grayStates, blackStates));
        testAccept(new EntryBlock(0), new EndBlock(1), ChainBlock::setNext,
                (block, callback, grayStates, blackStates) -> strategy.acceptPoint(block, callback, grayStates, blackStates));
    }

    private <T extends Block, E extends Block> void testAccept(T first, E second, BiConsumer<T, E> consumer, TestConsumer<E> testConsumer) {
        List<Block> expectedGray = Collections.singletonList(first);
        List<Block> expectedBlack = Collections.singletonList(second);

        Collection<Block> grayBlocks = new ArrayList<>();
        Collection<Block> blackBlocks = new ArrayList<>();

        BlockFlowchartContract callback = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                // test
            }
        };

        consumer.accept(first, second);
        testConsumer.accept(second, callback, grayBlocks, blackBlocks);

        Assertions.assertIterableEquals(expectedGray, grayBlocks);
        Assertions.assertIterableEquals(expectedBlack, blackBlocks);
    }

    interface TestConsumer<T extends Block> {

        void accept(T state, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks);

    }
}