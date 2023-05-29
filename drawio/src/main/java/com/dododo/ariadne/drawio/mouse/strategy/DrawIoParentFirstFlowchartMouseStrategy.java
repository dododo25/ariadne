package com.dododo.ariadne.drawio.mouse.strategy;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;

import java.util.LinkedList;
import java.util.Set;

public class DrawIoParentFirstFlowchartMouseStrategy implements DrawIoFlowchartMouseStrategy {

    @Override
    public void acceptChainBlock(ChainBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited) {
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
    public void acceptMenuBlock(MenuBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited) {
        if (!visited.contains(block)) {
            callback.accept(block);
            visited.add(block);

            block.branchesStream()
                    .forEach(mouse::accept);
        }
    }

    @Override
    public void acceptSwitchBlock(SwitchBlock block, DrawIoFlowchartMouse mouse, DrawIoFlowchartContract callback, Set<Block> visited) {
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
