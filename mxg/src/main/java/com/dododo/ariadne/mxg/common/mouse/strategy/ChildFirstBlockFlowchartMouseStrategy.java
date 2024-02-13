package com.dododo.ariadne.mxg.common.mouse.strategy;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContractAdapter;
import com.dododo.ariadne.mxg.common.model.AbstractPointBlock;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;

import java.util.Arrays;
import java.util.Collection;

public class ChildFirstBlockFlowchartMouseStrategy implements BlockFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);

        acceptRoots(block, grayBlocks, blackBlocks);
    }

    @Override
    public void acceptMenuBlock(MenuBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);

        acceptRoots(block, grayBlocks, blackBlocks);
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        callback.accept(block);

        acceptRoots(block, grayBlocks, blackBlocks);
    }

    @Override
    public void acceptPoint(AbstractPointBlock block, BlockFlowchartContract callback, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        grayBlocks.remove(block);

        if (blackBlocks.contains(block)) {
            return;
        }

        blackBlocks.add(block);
        block.accept(callback);

        acceptRoots(block, grayBlocks, blackBlocks);
    }

    private void acceptRoots(Block block, Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
        BlockFlowchartContract contract = new InnerFlowchartContract(grayBlocks, blackBlocks);

        Arrays.stream(block.getRoots())
                .forEach(root -> root.accept(contract));
    }

    protected static class InnerFlowchartContract extends BlockFlowchartContractAdapter {

        protected final Collection<Block> grayBlocks;

        protected final Collection<Block> blackBlocks;

        protected InnerFlowchartContract(Collection<Block> grayBlocks, Collection<Block> blackBlocks) {
            this.grayBlocks = grayBlocks;
            this.blackBlocks = blackBlocks;
        }

        @Override
        public void accept(EntryBlock block) {
            acceptChainBlock(block);
        }

        @Override
        public void accept(TextBlock block) {
            acceptChainBlock(block);
        }

        @Override
        public void accept(ReplyBlock block) {
            acceptChainBlock(block);
        }

        @Override
        public void accept(OptionBlock block) {
            acceptChainBlock(block);
        }

        @Override
        public void accept(ConditionalOptionBlock block) {
            acceptChainBlock(block);
        }

        @Override
        public void accept(MenuBlock block) {
            if (block.branchesCount() == 0 || block.branchesStream().allMatch(blackBlocks::contains)) {
                grayBlocks.add(block);
            }
        }

        @Override
        public void accept(SwitchBlock block) {
            if (block.getTrueBranch() == null && block.getFalseBranch() == null
                    || blackBlocks.contains(block.getTrueBranch())
                    || blackBlocks.contains(block.getFalseBranch())) {
                grayBlocks.add(block);
            }
        }

        protected void acceptChainBlock(ChainBlock block) {
            if (block.getNext() == null || blackBlocks.contains(block.getNext())) {
                grayBlocks.add(block);
            }
        }
    }
}
