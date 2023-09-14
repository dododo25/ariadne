package com.dododo.ariadne.block.factory;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.SwitchBlock;

import java.util.Objects;

public class SwitchBlockComparatorFactory extends BlockComparatorFactory {

    public SwitchBlockComparatorFactory(SwitchBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof SwitchBlock
                && Objects.equals(((SwitchBlock) o).getCondition(), ((SwitchBlock) block).getCondition()) ? 0 : 1;
    }
}
