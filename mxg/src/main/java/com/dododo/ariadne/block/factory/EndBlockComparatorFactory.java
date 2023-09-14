package com.dododo.ariadne.block.factory;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.EndBlock;

public class EndBlockComparatorFactory extends BlockComparatorFactory {

    public EndBlockComparatorFactory(EndBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EndBlock ? 0 : 1;
    }
}
