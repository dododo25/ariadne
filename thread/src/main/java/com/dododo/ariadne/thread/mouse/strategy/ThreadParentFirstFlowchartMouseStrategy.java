package com.dododo.ariadne.thread.mouse.strategy;

import com.dododo.ariadne.thread.contract.ThreadFlowchartContract;
import com.dododo.ariadne.thread.mouse.ThreadFlowchartMouse;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.thread.model.ChainBlock;
import com.dododo.ariadne.thread.model.MenuBlock;
import com.dododo.ariadne.thread.model.SwitchBlock;

import java.util.LinkedList;
import java.util.Set;

public class ThreadParentFirstFlowchartMouseStrategy implements ThreadFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            LinkedList<ChainBlock> blocks = prepareChain(block);

            for (Block b : blocks) {
                if (visited.contains(b)) {
                    break;
                }

                visited.add(b);
                b.accept(callback);
            }

            if (blocks.getLast().getNext() != null) {
                blocks.getLast().getNext().accept(mouse);
            }
        }
    }

    @Override
    public void acceptMenuBlock(MenuBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            callback.accept(block);
            visited.add(block);

            block.branchesStream()
                    .forEach(mouse::accept);
        }
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, ThreadFlowchartMouse mouse, ThreadFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            callback.accept(block);
            visited.add(block);

            if (block.getTrueBranch() != null) {
                block.getTrueBranch().accept(mouse);
            }

            if (block.getFalseBranch() != null) {
                block.getFalseBranch().accept(mouse);
            }
        }
    }

    private static LinkedList<ChainBlock> prepareChain(ChainBlock block) {
        LinkedList<ChainBlock> result = new LinkedList<>();
        Block b = block;

        while (b instanceof ChainBlock) {
            if (result.contains(b)) {
                break;
            }

            result.addLast((ChainBlock) b);
            b = ((ChainBlock) b).getNext();
        }

        return result;
    }
}
