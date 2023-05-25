package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContractAdapter;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoChildrenFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mxg.MxFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ApplyLayoutJob extends DrawIoAbstractJob {

    public static final int VERTICAL_PADDING = 40;

    public ApplyLayoutJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        Block rootBlock = getRootBlock();
        List<Block> appliedBlocks = new ArrayList<>();

        collectBlocks(rootBlock, appliedBlocks);

        appliedBlocks
                .forEach(this::applyLayout);
    }

    private void collectBlocks(Block root, List<Block> blocks) {
        DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {
            @Override
            public void accept(EntryBlock block) {
                blocks.add(block);
            }

            @Override
            public void accept(SwitchBlock block) {
                blocks.add(block);
            }
        };
        DrawIoFlowchartContract mouse = new DrawIoFlowchartMouse(callback,
                new DrawIoChildrenFirstFlowchartMouseStrategy());

        root.accept(mouse);
    }

    private void applyLayout(Block root) {
        DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {

            @Override
            public void accept(EntryBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(StatementBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(SwitchBlock block) {
                int width = block.getWidth();
                int height = block.getHeight();

                acceptBlock(block, block.getTrueBranch(), -block.getTrueBranch().getWidth(), height * 5 / 6);
                acceptBlock(block, block.getFalseBranch(), width, height * 5 / 6);
            }

            private void acceptChainBlock(ChainBlock block) {
                int width = block.getWidth();
                int height = block.getHeight();
                int nextBlockWidth = block.getNext().getWidth();

                acceptBlock(block, block.getNext(), (width - nextBlockWidth) / 2, height + VERTICAL_PADDING);
            }

            private void acceptBlock(Block root, Block block, int dx, int dy) {
                int oldX = block.getX();
                int oldY = block.getY();

                block.setX(root.getX() + dx);
                block.setY(root.getY() + dy);

                if (block.getY() < oldY) {
                    block.setX(oldX);
                    block.setY(oldY);
                }
            }
        };
        DrawIoFlowchartContract mouse = new DrawIoFlowchartMouse(callback,
                new DrawIoParentFirstFlowchartMouseStrategy());

        root.accept(mouse);
    }
}
