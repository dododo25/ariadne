package com.dododo.ariadne.drawio.mouse;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoFlowchartMouseStrategy;

import java.util.HashSet;
import java.util.Set;

public class DrawIoFlowchartMouse implements DrawIoFlowchartContract {

    private final DrawIoFlowchartContract callback;

    private final DrawIoFlowchartMouseStrategy strategy;

    private final Set<Block> visited;

    public DrawIoFlowchartMouse(DrawIoFlowchartContract callback, DrawIoFlowchartMouseStrategy strategy) {
        this.callback = callback;
        this.strategy = strategy;
        this.visited = new HashSet<>();
    }

    @Override
    public void accept(EntryBlock block) {
        strategy.acceptChainBlock(block, this, callback, visited);
    }

    @Override
    public void accept(StatementBlock block) {
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
