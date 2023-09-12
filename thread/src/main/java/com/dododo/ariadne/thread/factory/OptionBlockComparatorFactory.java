package com.dododo.ariadne.thread.factory;

import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.OptionBlock;

import java.util.Objects;

public class OptionBlockComparatorFactory extends BlockComparatorFactory {

    public OptionBlockComparatorFactory(OptionBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof OptionBlock
                && Objects.equals(((OptionBlock) o).getValue(), ((OptionBlock) block).getValue()) ? 0 : 1;
    }
}
