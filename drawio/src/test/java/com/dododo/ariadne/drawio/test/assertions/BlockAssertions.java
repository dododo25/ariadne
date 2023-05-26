package com.dododo.ariadne.drawio.test.assertions;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoSimpleFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;

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


        DrawIoFlowchartContract c1 = new DrawIoSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                blocks.add(block);
            }
        };

        DrawIoFlowchartContract c2 = new DrawIoSimpleFlowchartContract() {
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

        DrawIoFlowchartMouse m1 = new DrawIoFlowchartMouse(c1, new DrawIoParentFirstFlowchartMouseStrategy());
        DrawIoFlowchartMouse m2 = new DrawIoFlowchartMouse(c2, new DrawIoParentFirstFlowchartMouseStrategy());

        b1.accept(m1);
        b2.accept(m2);
    }
}
