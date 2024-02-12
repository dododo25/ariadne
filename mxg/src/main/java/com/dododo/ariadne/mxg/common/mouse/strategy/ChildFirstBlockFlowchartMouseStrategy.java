package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.AbstractPointBlock;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;

import java.util.Collection;

public class ChildFirstBlockFlowchartMouseStrategy implements BlockFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);
    }

    @Override
    public void acceptMenuBlock(MenuBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);
    }

    @Override
    public void acceptPoint(AbstractPointBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);
    }
}
