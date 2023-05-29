package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContractAdapter;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoChildrenFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mxg.MxFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public final class ApplyLayoutJob extends DrawIoAbstractJob {

    public static final int HORIZONTAL_PADDING = 40;

    public static final int VERTICAL_PADDING = 40;

    public ApplyLayoutJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        Block rootBlock = getRootBlock();
        List<Block> appliedBlocks = new ArrayList<>();

        collectBlocks(rootBlock, appliedBlocks);
        prepareMenus(rootBlock);

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
            public void accept(MenuBlock block) {
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

    private void prepareMenus(Block root) {
        DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {
            @Override
            public void accept(MenuBlock block) {
                block.setWidth(block.branchesStream()
                        .mapToInt(b -> b.getWidth() + VERTICAL_PADDING).sum() - VERTICAL_PADDING);
            }
        };
        DrawIoFlowchartContract mouse = new DrawIoFlowchartMouse(callback,
                new DrawIoParentFirstFlowchartMouseStrategy());

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
            public void accept(ReplyBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(OptionBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(ConditionalOptionBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(MenuBlock block) {
                IntStream.range(0, block.branchesCount()).forEach(index -> {
                    OptionBlock optionBlock = block.branchAt(index);
                    acceptBlock(block, optionBlock,
                                (optionBlock.getWidth() + HORIZONTAL_PADDING) * index, VERTICAL_PADDING);
                });
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
