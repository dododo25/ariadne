package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.MenuBlock;

public class MenuBlockComparatorFactory extends BlockComparatorFactory {

    public MenuBlockComparatorFactory(MenuBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof MenuBlock ? 0 : 1;
    }
}
