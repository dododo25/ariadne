package com.dododo.ariadne.thread.factory;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.TextBlock;

import java.util.Objects;

public class StatementBlockComparatorFactory extends BlockComparatorFactory {

    public StatementBlockComparatorFactory(TextBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof TextBlock
                && Objects.equals(((TextBlock) o).getValue(), ((TextBlock) block).getValue()) ? 0 : 1;
    }
}
