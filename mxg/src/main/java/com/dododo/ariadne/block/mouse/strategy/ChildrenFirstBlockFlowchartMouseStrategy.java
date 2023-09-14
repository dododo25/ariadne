package com.dododo.ariadne.block.mouse.strategy;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ChainBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.mouse.BlockFlowchartMouse;

import java.util.LinkedList;
import java.util.Set;

public class ChildrenFirstBlockFlowchartMouseStrategy implements BlockFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            visited.add(block);

            LinkedList<ChainBlock> blocks = prepareChain(block);

            if (!blocks.isEmpty()) {
                if (blocks.getFirst().getNext() != null) {
                    blocks.getFirst().getNext().accept(mouse);
                }

                for (Block b : blocks) {
                    if (visited.contains(b)) {
                        break;
                    }

                    visited.add(b);
                    b.accept(callback);
                }
            } else if (block.getNext() != null) {
                block.getNext().accept(mouse);
            }

            block.accept(callback);
        }
    }

    @Override
    public void acceptMenuBlock(MenuBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            visited.add(block);

            block.branchesStream()
                    .forEach(mouse::accept);

            callback.accept(block);
        }
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            visited.add(block);

            if (block.getTrueBranch() != null) {
                block.getTrueBranch().accept(mouse);
            }

            if (block.getFalseBranch() != null) {
                block.getFalseBranch().accept(mouse);
            }

            callback.accept(block);
        }
    }

    private static LinkedList<ChainBlock> prepareChain(ChainBlock block) {
        LinkedList<ChainBlock> result = new LinkedList<>();
        Block b = block.getNext();

        while (b instanceof ChainBlock) {
            if (result.contains(b)) {
                break;
            }

            result.addFirst((ChainBlock) b);
            b = ((ChainBlock) b).getNext();
        }

        return result;
    }
}
