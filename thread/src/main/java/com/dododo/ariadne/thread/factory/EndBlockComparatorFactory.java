package com.dododo.ariadne.thread.factory;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.EndBlock;

public class EndBlockComparatorFactory extends BlockComparatorFactory {

    public EndBlockComparatorFactory(EndBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EndBlock ? 0 : 1;
    }
}
