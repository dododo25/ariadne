package com.dododo.ariadne.thread.factory;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.EntryBlock;

public class EntryBlockComparatorFactory extends BlockComparatorFactory {

    public EntryBlockComparatorFactory(EntryBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EntryBlock ? 0 : 1;
    }
}
