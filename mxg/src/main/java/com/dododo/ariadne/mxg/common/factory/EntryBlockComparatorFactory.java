package com.dododo.ariadne.mxg.common.factory;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.EntryBlock;

public class EntryBlockComparatorFactory extends BlockComparatorFactory {

    public EntryBlockComparatorFactory(EntryBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EntryBlock ? 0 : 1;
    }
}
