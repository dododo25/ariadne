package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;

public abstract class BlockComparatorFactory {

    protected final Block block;

    protected BlockComparatorFactory(Block block) {
        this.block = block;
    }

    public abstract Comparable<Block> createSimpleComparator();

    public Comparable<Block> createWithPositionComparator() {
        Comparable<Block> comparable = createSimpleComparator();

        return o -> comparable.compareTo(o) == 0
                && o.getX() == block.getX()
                && o.getY() == block.getY()
                && o.getWidth() == block.getWidth()
                && o.getHeight() == block.getHeight() ? 0 : 1;
    }
}
