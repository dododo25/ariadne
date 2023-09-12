package com.dododo.ariadne.thread.factory;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.MenuBlock;

public class MenuBlockComparatorFactory extends BlockComparatorFactory {

    public MenuBlockComparatorFactory(MenuBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof MenuBlock ? 0 : 1;
    }
}
