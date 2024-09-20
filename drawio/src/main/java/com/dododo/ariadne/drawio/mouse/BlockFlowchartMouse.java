package com.dododo.ariadne.drawio.mouse;

import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.drawio.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.model.TextBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class BlockFlowchartMouse {

    public void accept(Block block, Consumer<Block> consumer) {
        BlockFlowchartContract callback = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                consumer.accept(block);
            }
        };

        accept(block, callback);
    }

    public void accept(Block block, BlockFlowchartContract callback) {
        Collection<Block> blackBlocks = new ArrayList<>();
        List<Block> grayBlocks = new ArrayList<>();

        grayBlocks.add(block);

        while (!grayBlocks.isEmpty()) {
            Block current = grayBlocks.remove(0);

            if (blackBlocks.contains(current)) {
                continue;
            }

            InnerBlockFlowchartContract contract = new InnerBlockFlowchartContract(callback);

            blackBlocks.add(current);
            current.accept(contract);

            grayBlocks.addAll(0, contract.grayBlocks);
        }
    }

    private static class InnerBlockFlowchartContract implements BlockFlowchartContract {

        private final BlockFlowchartContract callback;

        private final List<Block> grayBlocks;

        public InnerBlockFlowchartContract(BlockFlowchartContract callback) {
            this.callback = callback;
            this.grayBlocks = new ArrayList<>();
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
        public void accept(MenuBlock block) {
            callback.accept(block);

            block.branchesStream()
                    .forEach(grayBlocks::add);
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
        public void accept(SwitchBlock block) {
            callback.accept(block);

            if (block.getTrueBranch() != null) {
                grayBlocks.add(block.getTrueBranch());
            }

            if (block.getFalseBranch() != null) {
                grayBlocks.add(block.getFalseBranch());
            }
        }

        @Override
        public void accept(EndBlock block) {

            block.accept(callback);
        }

        public void acceptChainBlock(ChainBlock block) {
            block.accept(callback);

            if (block.getNext() != null) {
                grayBlocks.add(block.getNext());
            }
        }
    }
}
