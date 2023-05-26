package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EntryBlock;

public class EntryBlockComparatorFactory extends BlockComparatorFactory {

    public EntryBlockComparatorFactory(EntryBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EntryBlock ? 0 : 1;
    }
}
