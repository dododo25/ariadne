package com.dododo.ariadne.block.factory;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.EntryBlock;

public class EntryBlockComparatorFactory extends BlockComparatorFactory {

    public EntryBlockComparatorFactory(EntryBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EntryBlock ? 0 : 1;
    }
}
