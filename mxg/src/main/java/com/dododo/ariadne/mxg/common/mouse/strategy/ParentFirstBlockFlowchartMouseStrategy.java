package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.AbstractPointBlock;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;

import java.util.Collection;

public class ParentFirstBlockFlowchartMouseStrategy implements BlockFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);

        if (block.getNext() != null) {
            grayBlocks.add(block.getNext());
        }
    }

    @Override
    public void acceptMenuBlock(MenuBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);

        block.branchesStream()
                .forEach(grayBlocks::add);
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);

        if (block.getTrueBranch() != null) {
            grayBlocks.add(block.getTrueBranch());
        }

        if (block.getFalseBranch() != null) {
            grayBlocks.add(block.getFalseBranch());
        }
    }

    @Override
    public void acceptPoint(AbstractPointBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);
    }
}
