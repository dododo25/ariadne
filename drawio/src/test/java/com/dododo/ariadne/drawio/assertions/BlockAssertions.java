package com.dododo.ariadne.drawio.assertions;

import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.mouse.BlockFlowchartMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockAssertions {

    private BlockAssertions() {}

    public static void assertEquals(Block b1, Block b2,
                                    Function<BlockComparatorFactory, Comparable<Block>> comparableSelector) {
        AtomicBoolean stopProcessingRef = new AtomicBoolean();

        List<Block> blocks = new ArrayList<>();

        Consumer<Block> c1 = blocks::add;
        Consumer<Block> c2 = block -> {
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
        };

        if (b1 == b2) {
            return;
        }

        BlockFlowchartMouse m1 = new BlockFlowchartMouse();
        BlockFlowchartMouse m2 = new BlockFlowchartMouse();

        m1.accept(b1, c1);
        m2.accept(b2, c2);
    }
}
