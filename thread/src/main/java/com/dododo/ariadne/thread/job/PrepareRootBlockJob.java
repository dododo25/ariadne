package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ChainBlock;
import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.mxg.MxFile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class PrepareRootBlockJob extends ThreadAbstractJob {

    public PrepareRootBlockJob(AtomicReference<MxFile> mxFileRef,
                               AtomicReference<JaxbState> jaxbStateRef,
                               AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
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

        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                blocks.put(state, new EntryBlock(ref.getAndIncrement()));
            }

            @Override
            public void accept(Text text) {
                blocks.put(text, new TextBlock(ref.getAndIncrement(), text.getValue()));
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
            public void accept(Text text) {
                acceptChainState(text);
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

                menu.branchesStream().forEach(option ->
                        menuBlock.addBranch((OptionBlock) blocks.get(option)));
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
