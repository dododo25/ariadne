package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.SwitchBlock;

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
