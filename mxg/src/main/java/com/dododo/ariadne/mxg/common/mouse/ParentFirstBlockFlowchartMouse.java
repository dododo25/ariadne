package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.mouse.strategy.ParentFirstBlockFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ParentFirstBlockFlowchartMouse extends BlockFlowchartMouse {

    public ParentFirstBlockFlowchartMouse() {
        this(new ParentFirstBlockFlowchartMouseStrategy());
    }

    protected ParentFirstBlockFlowchartMouse(ParentFirstBlockFlowchartMouseStrategy strategy) {
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
        Collection<Block> grayBlocks = new ArrayList<>();
        Collection<Block> blackBlocks = new ArrayList<>();

        block.accept(strategy, callback, grayBlocks, blackBlocks);

        while (!grayBlocks.isEmpty()) {
            grayBlocks.stream()
                    .findFirst()
                    .ifPresent(nextState -> nextState.accept(strategy, callback, grayBlocks, blackBlocks));
        }
    }
}
