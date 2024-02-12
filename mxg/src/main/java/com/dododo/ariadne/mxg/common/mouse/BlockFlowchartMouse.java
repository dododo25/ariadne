package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.mouse.strategy.BlockFlowchartMouseStrategy;

import java.util.function.Consumer;

public abstract class BlockFlowchartMouse {

    protected final BlockFlowchartMouseStrategy strategy;

    protected BlockFlowchartMouse(BlockFlowchartMouseStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void accept(Block block, Consumer<Block> consumer);

    public abstract void accept(Block block, BlockFlowchartContract callback);
}
