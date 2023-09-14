package com.dododo.ariadne.mt.assertions;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.block.factory.BlockComparatorFactory;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.mouse.BlockFlowchartMouse;
import com.dododo.ariadne.block.mouse.strategy.ParentFirstBlockFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class BlockAssertions {

    private BlockAssertions() {}

    public static void assertEquals(Block b1, Block b2,
                                    Function<BlockComparatorFactory, Comparable<Block>> comparableSelector) {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<Block> blocks = new ArrayList<>();

        BlockFlowchartContract c1 = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                blocks.add(block);
            }
        };

        BlockFlowchartContract c2 = new BlockSimpleFlowchartContract() {
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

        BlockFlowchartMouse m1 = new BlockFlowchartMouse(c1, new ParentFirstBlockFlowchartMouseStrategy());
        BlockFlowchartMouse m2 = new BlockFlowchartMouse(c2, new ParentFirstBlockFlowchartMouseStrategy());

        b1.accept(m1);
        b2.accept(m2);
    }
}
