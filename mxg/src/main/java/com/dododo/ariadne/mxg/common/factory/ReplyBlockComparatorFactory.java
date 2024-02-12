package com.dododo.ariadne.mxg.common.factory;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;

import java.util.Objects;

public class ReplyBlockComparatorFactory extends BlockComparatorFactory {

    public ReplyBlockComparatorFactory(ReplyBlock block) {
        super(block);
    }

    @Override
    public Comparable<Block> createSimpleComparator() {
        return o -> o instanceof ReplyBlock
                && Objects.equals(((ReplyBlock) o).getCharacter(), ((ReplyBlock) block).getCharacter())
                && Objects.equals(((ReplyBlock) o).getLine(), ((ReplyBlock) block).getLine()) ? 0 : 1;
    }
}
