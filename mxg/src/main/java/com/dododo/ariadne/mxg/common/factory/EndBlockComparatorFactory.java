package com.dododo.ariadne.mxg.common.factory;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.EndBlock;

public class EndBlockComparatorFactory extends BlockComparatorFactory {

    public EndBlockComparatorFactory(EndBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EndBlock ? 0 : 1;
    }
}
