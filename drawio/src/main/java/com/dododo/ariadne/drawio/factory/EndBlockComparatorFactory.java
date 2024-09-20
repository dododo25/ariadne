package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EndBlock;

public class EndBlockComparatorFactory extends BlockComparatorFactory {

    public EndBlockComparatorFactory(EndBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof EndBlock ? 0 : 1;
    }
}
