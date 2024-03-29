package com.dododo.ariadne.block.factory;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.MenuBlock;

public class MenuBlockComparatorFactory extends BlockComparatorFactory {

    public MenuBlockComparatorFactory(MenuBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof MenuBlock ? 0 : 1;
    }
}
