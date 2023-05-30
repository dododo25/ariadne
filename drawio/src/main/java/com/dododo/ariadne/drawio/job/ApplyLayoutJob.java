package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContractAdapter;
import com.dododo.ariadne.drawio.contract.DrawIoSimpleFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mxg.MxFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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

        Collection<Block> layoutBlocks = new ArrayList<>();
        Map<Block, Map<Block, SwitchBranch>> loopBlocks = new HashMap<>();

        prepareMenus(rootBlock);

        collectBlocks(rootBlock, layoutBlocks);
        collectLoopBlocks(rootBlock, loopBlocks);

        unwrapLoops(loopBlocks);
        layoutBlocks.forEach(this::applyLayout);
        wrapLoops(loopBlocks);
    }

    private void prepareMenus(Block root) {
        DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {
            @Override
            public void accept(MenuBlock block) {
                block.setWidth(block.branchesStream()
                        .mapToInt(b -> b.getWidth() + VERTICAL_PADDING).sum() - VERTICAL_PADDING);
            }
        };

        runMouse(root, callback);
    }

    private void collectBlocks(Block root, Collection<Block> blocks) {
        DrawIoFlowchartContract callback = new DrawIoSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                if (block.getRoots().length != 1) {
                    blocks.add(block);
                }
            }
        };

        runMouse(root, callback);
    }

    private void collectLoopBlocks(Block root, Map<Block, Map<Block, SwitchBranch>> map) {
        DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {

            private final Set<Block> visited = new HashSet<>();

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
                block.branchesStream()
                        .forEach(option -> acceptBlock(block, option));
            }

            @Override
            public void accept(SwitchBlock block) {
                acceptBlock(block, block.getTrueBranch());
                acceptBlock(block, block.getFalseBranch());
            }

            private void acceptChainBlock(ChainBlock block) {
                acceptBlock(block, block.getNext());
            }

            private void acceptBlock(Block root, Block child) {
                if (root.getRoots().length > 1 && !visited.contains(child)) {
                    Set<Block> visitedBlocks = new HashSet<>(visited);
                    boolean res = collectLoopBlocks(root, child, map, visitedBlocks);

                    if (res) {
                        visited.addAll(visitedBlocks);
                    }
                }
            }
        };

        runMouse(root, callback);
    }

    private void unwrapLoops(Map<Block, Map<Block, SwitchBranch>> map) {
        map.forEach((mainBlock, roots) -> roots.keySet().forEach(root -> {
            DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {
                @Override
                public void accept(EntryBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(StatementBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(ReplyBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(OptionBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(ConditionalOptionBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(SwitchBlock block) {
                    if (block.getTrueBranch() == mainBlock) {
                        block.setTrueBranch(null);
                    }

                    if (block.getFalseBranch() == mainBlock) {
                        block.setFalseBranch(null);
                    }
                }
            };

            root.accept(callback);
        }));
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

                if (block.getTrueBranch() != null) {
                    acceptBlock(block, block.getTrueBranch(), -block.getTrueBranch().getWidth(), height * 5 / 6);
                }

                if (block.getFalseBranch() != null) {
                    acceptBlock(block, block.getFalseBranch(), width, height * 5 / 6);
                }
            }

            private void acceptChainBlock(ChainBlock block) {
                if (block.getNext() != null) {
                    int width = block.getWidth();
                    int height = block.getHeight();
                    int nextBlockWidth = block.getNext().getWidth();

                    acceptBlock(block, block.getNext(), -(nextBlockWidth - width) / 2, height + VERTICAL_PADDING);
                }
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

        runMouse(root, callback);
    }

    private void wrapLoops(Map<Block, Map<Block, SwitchBranch>> map) {
        map.forEach((b, roots) -> roots.forEach((root, branch) -> {
            DrawIoFlowchartContract callback = new DrawIoFlowchartContractAdapter() {
                @Override
                public void accept(EntryBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(StatementBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(ReplyBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(OptionBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(ConditionalOptionBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(SwitchBlock block) {
                    if (branch == SwitchBranch.TRUE) {
                        block.setTrueBranch(b);
                    }

                    if (branch == SwitchBranch.FALSE) {
                        block.setFalseBranch(b);
                    }
                }
            };

            root.accept(callback);
        }));
    }

    private boolean collectLoopBlocks(Block root, Block child, Map<Block, Map<Block, SwitchBranch>> map,
                                      Set<Block> visited) {
        AtomicBoolean result = new AtomicBoolean();

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
            public void accept(SwitchBlock block) {
                accept(block, block.getTrueBranch(), SwitchBranch.TRUE);
                accept(block, block.getFalseBranch(), SwitchBranch.FALSE);
            }

            private void acceptChainBlock(ChainBlock block) {
                accept(block, block.getNext(), SwitchBranch.NONE);
            }

            private void accept(Block rootBlock, Block childBlock, SwitchBranch branch) {
                if (childBlock == root) {
                    map.computeIfAbsent(root, i -> new HashMap<>()).put(rootBlock, branch);
                    result.set(true);
                }

                visited.add(rootBlock);
            }
        };

        runMouse(child, callback);
        return result.get();
    }

    private void runMouse(Block block, DrawIoFlowchartContract contract) {
        DrawIoFlowchartContract mouse = new DrawIoFlowchartMouse(contract,
                new DrawIoParentFirstFlowchartMouseStrategy());

        block.accept(mouse);
    }

    private enum SwitchBranch {
        NONE, TRUE, FALSE
    }
}
