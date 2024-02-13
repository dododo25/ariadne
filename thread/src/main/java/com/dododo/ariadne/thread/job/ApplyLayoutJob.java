package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContractAdapter;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ChainBlock;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import com.dododo.ariadne.mxg.common.mouse.BlockFlowchartMouse;
import com.dododo.ariadne.mxg.common.mouse.ParentFirstBlockFlowchartMouse;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.mxg.model.MxFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public final class ApplyLayoutJob extends ThreadAbstractJob {

    public static final int HORIZONTAL_PADDING = 40;

    public static final int VERTICAL_PADDING = 40;

    private final BlockFlowchartMouse mouse;

    public ApplyLayoutJob(AtomicReference<MxFile> mxFileRef,
                          AtomicReference<JaxbState> jaxbStateRef,
                          AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
        this.mouse = new ParentFirstBlockFlowchartMouse();
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
        BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {
            @Override
            public void accept(MenuBlock block) {
                block.setWidth(block.branchesStream()
                        .mapToInt(b -> b.getWidth() + VERTICAL_PADDING).sum() - VERTICAL_PADDING);
            }
        };

        mouse.accept(root, callback);
    }

    private void collectBlocks(Block root, Collection<Block> blocks) {
        BlockFlowchartContract callback = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                if (block.getRoots().length != 1) {
                    blocks.add(block);
                }
            }
        };

        mouse.accept(root, callback);
    }

    private void collectLoopBlocks(Block root, Map<Block, Map<Block, SwitchBranch>> map) {
        BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {

            private final Set<Block> visited = new HashSet<>();

            @Override
            public void accept(EntryBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(TextBlock block) {
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

        mouse.accept(root, callback);
    }

    private void unwrapLoops(Map<Block, Map<Block, SwitchBranch>> map) {
        map.forEach((mainBlock, roots) -> roots.keySet().forEach(root -> {
            BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {
                @Override
                public void accept(EntryBlock block) {
                    block.setNext(null);
                }

                @Override
                public void accept(TextBlock block) {
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
        BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {

            @Override
            public void accept(EntryBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(TextBlock block) {
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

        mouse.accept(root, callback);
    }

    private void wrapLoops(Map<Block, Map<Block, SwitchBranch>> map) {
        map.forEach((b, roots) -> roots.forEach((root, branch) -> {
            BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {
                @Override
                public void accept(EntryBlock block) {
                    block.setNext(b);
                }

                @Override
                public void accept(TextBlock block) {
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

        BlockFlowchartContract callback = new BlockFlowchartContractAdapter() {

            @Override
            public void accept(EntryBlock block) {
                acceptChainBlock(block);
            }

            @Override
            public void accept(TextBlock block) {
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

        mouse.accept(child, callback);
        return result.get();
    }

    private enum SwitchBranch {
        NONE, TRUE, FALSE
    }
}
