package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
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
        BlockFlowchartContract callback = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                consumer.accept(block);
            }
        };

        accept(block, callback);
    }

    @Override
    public void accept(Block block, BlockFlowchartContract callback) {
        Collection<Block> grayBlocks = prepareStartingPoints(block);
        Collection<Block> blackBlocks = new ArrayList<>();

        while (!grayBlocks.isEmpty()) {
            grayBlocks.stream().findFirst().ifPresent(nextState ->
                    nextState.accept(strategy, callback, grayBlocks, blackBlocks));
        }
    }

    protected Collection<Block> prepareStartingPoints(Block block) {
        Collection<Block> result = new ArrayList<>();

        BlockFlowchartContract callback = new InnerFlowchartContract(result);
        BlockFlowchartMouse mouse = new ParentFirstBlockFlowchartMouse();

        mouse.accept(block, callback);

        return result;
    }

    protected static class InnerFlowchartContract implements BlockFlowchartContract {

        protected final Collection<Block> result;

        protected final Collection<Block> blackBlocks;

        public InnerFlowchartContract(Collection<Block> result) {
            this.result = result;
            this.blackBlocks = new HashSet<>();
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

            blackBlocks.add(block);

            if (block.branchesCount() == 0 || block.branchesStream().allMatch(blackBlocks::contains)) {
                result.add(block);
            }
        }

        @Override
        public void accept(SwitchBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            blackBlocks.add(block);

            if ((block.getTrueBranch() == null
                    && block.getFalseBranch() == null)
                    || (blackBlocks.contains(block.getTrueBranch())
                    && blackBlocks.contains(block.getFalseBranch()))) {
                result.add(block);
            }
        }

        @Override
        public void accept(EndBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            blackBlocks.add(block);
            result.add(block);
        }

        protected void acceptChainBlock(ChainBlock block) {
            if (blackBlocks.contains(block)) {
                return;
            }

            blackBlocks.add(block);

            if (block.getNext() == null || blackBlocks.contains(block.getNext())) {
                result.add(block);
            }
        }
    }
}
