package com.dododo.ariadne.drawio.factory;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ReplyBlock;

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
