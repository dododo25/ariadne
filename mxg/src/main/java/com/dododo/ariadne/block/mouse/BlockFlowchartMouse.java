package com.dododo.ariadne.block.mouse;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;
import com.dododo.ariadne.block.mouse.strategy.BlockFlowchartMouseStrategy;

import java.util.HashSet;
import java.util.Set;

public class BlockFlowchartMouse implements BlockFlowchartContract {

    private final BlockFlowchartContract callback;

    private final BlockFlowchartMouseStrategy strategy;

    private final Set<Block> visited;

    public BlockFlowchartMouse(BlockFlowchartContract callback, BlockFlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
        this.visited = new HashSet<>();
    }

    @Override
    public void accept(EntryBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(TextBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(ReplyBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(OptionBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(ConditionalOptionBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(EndBlock block) {
        if (!visited.contains(block)) {
            block.accept(callback);
            visited.add(block);
        }
    }

    @Override
    public void accept(MenuBlock block) {
        strategy.acceptMenuBlock(block, this, callback, visited);
    }

    @Override
    public void accept(SwitchBlock block) {
        strategy.acceptSwitchBlock(block, this, callback, visited);
    }
}
