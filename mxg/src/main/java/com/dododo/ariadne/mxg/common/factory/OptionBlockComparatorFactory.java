package com.dododo.ariadne.mxg.common.factory;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.OptionBlock;

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
