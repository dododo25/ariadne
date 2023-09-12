package com.dododo.ariadne.thread.mouse;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.EndBlock;
import com.dododo.ariadne.thread.model.EntryBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.OptionBlock;
import com.dododo.ariadne.thread.model.ConditionalOptionBlock;
import com.dododo.ariadne.thread.model.ReplyBlock;
import com.dododo.ariadne.thread.model.TextBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;
import com.dododo.ariadne.thread.mouse.strategy.ThreadFlowchartMouseStrategy;

import java.util.HashSet;
import java.util.Set;

public class ThreadFlowchartMouse implements ThreadFlowchartContract {

    private final ThreadFlowchartContract callback;

    private final ThreadFlowchartMouseStrategy strategy;

    private final Set<Block> visited;

    public ThreadFlowchartMouse(ThreadFlowchartContract callback, ThreadFlowchartMouseStrategy strategy) {
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
