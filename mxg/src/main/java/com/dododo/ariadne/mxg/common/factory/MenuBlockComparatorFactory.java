package com.dododo.ariadne.mxg.common.factory;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.MenuBlock;

public class MenuBlockComparatorFactory extends BlockComparatorFactory {

    public MenuBlockComparatorFactory(MenuBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof MenuBlock ? 0 : 1;
    }
}
