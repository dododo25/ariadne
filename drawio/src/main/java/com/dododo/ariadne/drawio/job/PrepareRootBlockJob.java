package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mxg.MxFile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class PrepareRootBlockJob extends DrawIoAbstractJob {

    public PrepareRootBlockJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        Map<State, Block> blocks = new HashMap<>();

        State flowchart = getFlowchart();

        collectBlocks(blocks, flowchart);
        combineBlocks(blocks, flowchart);
        setRootBlock(blocks.get(flowchart));
    }

    private void collectBlocks(Map<State, Block> blocks, State flowchart) {
        AtomicInteger ref = new AtomicInteger();

        FlowchartContract contract = new FlowchartContract() {

            @Override
            public void accept(EntryState state) {
                blocks.put(state, new EntryBlock(ref.getAndIncrement()));
            }

            @Override
            public void accept(Statement statement) {
                blocks.put(statement, new StatementBlock(ref.getAndIncrement(), statement.getValue()));
            }

            @Override
            public void accept(Reply reply) {
                blocks.put(reply, new ReplyBlock(ref.getAndIncrement(), reply.getCharacter(), reply.getLine()));
            }

            @Override
            public void accept(Option option) {
                blocks.put(option, new OptionBlock(ref.getAndIncrement(), option.getValue()));
            }

            @Override
            public void accept(ConditionalOption option) {
                blocks.put(option, new ConditionalOptionBlock(ref.getAndIncrement(), option.getValue(),
                        option.getCondition()));
            }

            @Override
            public void accept(Menu menu) {
                blocks.put(menu, new MenuBlock(ref.getAndIncrement()));
            }

            @Override
            public void accept(Switch aSwitch) {
                blocks.put(aSwitch, new SwitchBlock(ref.getAndIncrement(), aSwitch.getCondition()));
            }

            @Override
            public void accept(EndPoint point) {
                blocks.put(point, new EndBlock(ref.getAndIncrement()));
            }
        };
        FlowchartMouse mouse = new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
        flowchart.accept(mouse);
    }

    private void combineBlocks(Map<State, Block> blocks, State flowchart) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Statement statement) {
                acceptChainState(statement);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainState(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainState(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainState(option);
            }

            @Override
            public void accept(Menu menu) {
                MenuBlock menuBlock = (MenuBlock) blocks.get(menu);

                menu.branchesStream().forEach(option -> {
                    OptionBlock optionBlock = (OptionBlock) blocks.get(option);

                    menuBlock.addBranch(optionBlock);
                    optionBlock.addRoot(menuBlock);
                });
            }

            @Override
            public void accept(Switch aSwitch) {
                SwitchBlock block = (SwitchBlock) blocks.get(aSwitch);

                if (aSwitch.getTrueBranch() != null) {
                    Block trueCheckBlock = blocks.get(aSwitch.getTrueBranch());
                    block.setTrueBranch(trueCheckBlock);
                }

                if (aSwitch.getFalseBranch() != null) {
                    Block falseCheckBlock = blocks.get(aSwitch.getFalseBranch());
                    block.setFalseBranch(falseCheckBlock);
                }
            }

            private void acceptChainState(ChainState state) {
                ChainBlock currentBlock = (ChainBlock) blocks.get(state);

                if (state.getNext() != null) {
                    Block nextBlock = blocks.get(state.getNext());
                    currentBlock.setNext(nextBlock);
                }
            }
        };
        FlowchartMouse mouse = new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
        flowchart.accept(mouse);
    }
}
