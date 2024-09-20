package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.OptionBlock;

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
