package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.StatementBlock;

import java.util.Objects;

public class StatementBlockComparatorFactory extends BlockComparatorFactory {

    public StatementBlockComparatorFactory(StatementBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof StatementBlock
                && Objects.equals(((StatementBlock) o).getValue(), ((StatementBlock) block).getValue()) ? 0 : 1;
    }
}
