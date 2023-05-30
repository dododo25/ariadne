package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;

import java.util.Objects;

public class ConditionalOptionBlockComparatorFactory extends BlockComparatorFactory {

    public ConditionalOptionBlockComparatorFactory(ConditionalOptionBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof ConditionalOptionBlock
                && Objects.equals(((ConditionalOptionBlock) o).getValue(), ((ConditionalOptionBlock) block).getValue())
                && Objects.equals(((ConditionalOptionBlock) o).getCondition(),
                    ((ConditionalOptionBlock) block).getCondition()) ? 0 : 1;
    }
}
