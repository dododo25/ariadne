package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import com.dododo.ariadne.mxg.common.mouse.strategy.ChildFirstBlockFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstBlockFlowchartMouse extends BlockFlowchartMouse {

    public ChildFirstBlockFlowchartMouse() {
        this(new ChildFirstBlockFlowchartMouseStrategy());
    }

    protected ChildFirstBlockFlowchartMouse(ChildFirstBlockFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void accept(Block block, Consumer<Block> consumer) {
        BlockFlowchartContract callback = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                consumer.accept(block);
            }
        };

        accept(block, callback);
    }

    @Override
    public void accept(Block block, BlockFlowchartContract callback) {
        Collection<Block> blackBlocks = new ArrayList<>();
        Collection<Block> grayBlocks = prepareStartingPoints(block, blackBlocks);

        while (!grayBlocks.isEmpty()) {
            grayBlocks.forEach(nextState ->
                    nextState.accept(strategy, callback, new HashSet<>(), blackBlocks));

            grayBlocks = prepareStartingPoints(block, blackBlocks);
        }
    }

    protected Collection<Block> prepareStartingPoints(Block block, Collection<Block> blackBlocks) {
        Collection<Block> result = new ArrayList<>();

        BlockFlowchartContract callback = new InnerFlowchartContract(result, blackBlocks);
        BlockFlowchartMouse mouse = new ParentFirstBlockFlowchartMouse();

        mouse.accept(block, callback);

        return result;
    }

    protected static class InnerFlowchartContract implements BlockFlowchartContract {

        protected final Collection<Block> result;

        protected final Collection<Block> blackBlocks;

        public InnerFlowchartContract(Collection<Block> result, Collection<Block> blackBlocks) {
            this.result = result;
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
            if (blackBlocks.contains(block)) {
                return;
            }

            if (block.branchesCount() == 0 || block.branchesStream().allMatch(blackBlocks::contains)) {
                result.add(block);
            }
        }

        @Override
        public void accept(SwitchBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            if (blackBlocks.contains(block.getTrueBranch()) && blackBlocks.contains(block.getFalseBranch())) {
                result.add(block);
            }

            if (block.getTrueBranch() == null && block.getFalseBranch() == null
                    || block.getTrueBranch() == block
                    || block.getFalseBranch() == block) {
                result.add(block);
            }
        }

        @Override
        public void accept(EndBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            result.add(block);
        }

        protected void acceptChainBlock(ChainBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            if (block.getNext() == null || blackBlocks.contains(block.getNext())) {
                result.add(block);
            }
        }
    }
}
