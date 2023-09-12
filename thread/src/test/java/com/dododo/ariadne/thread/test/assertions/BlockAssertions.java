package com.dododo.ariadne.thread.test.assertions;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.contract.ThreadSimpleFlowchartContract;
import com.dododo.ariadne.thread.factory.BlockComparatorFactory;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import com.dododo.ariadne.thread.mouse.strategy.ThreadParentFirstFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class BlockAssertions {

    private BlockAssertions() {}

    public static void assertEquals(Block b1,
                                    Block b2,
                                    Function<BlockComparatorFactory, Comparable<Block>> comparableSelector) {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<Block> blocks = new ArrayList<>();


        ThreadFlowchartContract c1 = new ThreadSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                blocks.add(block);
            }
        };

        ThreadFlowchartContract c2 = new ThreadSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                if (stopProcessingRef.get()) {
                    return;
                }

                if (blocks.isEmpty()) {
                    throw new AssertionError(String.format("Unexpected block %s", block));
                }

                Block b = blocks.remove(0);
                Comparable<Block> comparable = comparableSelector.apply(b.getFactory());

                if (comparable.compareTo(block) != 0) {
                    throw new AssertionError(String.format("Expected %s, got %s", b, block));
                } else if (b == block) {
                    stopProcessingRef.set(true);
                }
            }
        };

        if (b1 == b2) {
            return;
        }

        ThreadFlowchartMouse m1 = new ThreadFlowchartMouse(c1, new ThreadParentFirstFlowchartMouseStrategy());
        ThreadFlowchartMouse m2 = new ThreadFlowchartMouse(c2, new ThreadParentFirstFlowchartMouseStrategy());

        b1.accept(m1);
        b2.accept(m2);
    }
}
