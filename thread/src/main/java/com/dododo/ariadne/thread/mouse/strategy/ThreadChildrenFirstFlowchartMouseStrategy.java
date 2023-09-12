package com.dododo.ariadne.thread.mouse.strategy;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.ChainBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

import java.util.LinkedList;
import java.util.Set;

public class ThreadChildrenFirstFlowchartMouseStrategy implements ThreadFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
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
    public void acceptMenuBlock(MenuBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            visited.add(block);

            block.branchesStream()
                    .forEach(mouse::accept);

            callback.accept(block);
        }
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
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
