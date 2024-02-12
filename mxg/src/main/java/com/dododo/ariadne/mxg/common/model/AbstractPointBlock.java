package com.dododo.ariadne.mxg.common.model;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.mouse.strategy.BlockFlowchartMouseStrategy;

import java.util.Collection;

public abstract class AbstractPointBlock extends Block {

    protected AbstractPointBlock(int id) {
        super(id);
    }

    @Override
    public final void accept(BlockFlowchartMouseStrategy strategy, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        strategy.acceptPoint(this, callback, grayBlocks, blackBlocks);
    }
}
