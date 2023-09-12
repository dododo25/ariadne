package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
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
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.mxg.MxFile;
import com.dododo.ariadne.thread.model.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class PrepareJaxbStateJob extends ThreadAbstractJob {

    public PrepareJaxbStateJob(AtomicReference<MxFile> mxFileRef,
                               AtomicReference<JaxbState> jaxbStateRef,
                               AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
    }

    @Override
    public void run() {
        Map<State, JaxbState> stateMap = new HashMap<>();
        State flowchart = getFlowchart();

        collectBlocks(stateMap, flowchart);
        prepareSwitchBlocks(stateMap, flowchart);
        combineBlocks(stateMap, flowchart);
        setJaxbState(stateMap.get(flowchart));
    }

    private void collectBlocks(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContract() {

            @Override
            public void accept(EntryState state) {
                stateMap.put(state, new JaxbRootState());
            }

            @Override
            public void accept(Text text) {
                stateMap.put(text, new JaxbText(text.getValue()));
            }

            @Override
            public void accept(Reply reply) {
                stateMap.put(reply, new JaxbReply(reply.getCharacter(), reply.getLine()));
            }

            @Override
            public void accept(Option option) {
                stateMap.put(option, new JaxbOption(option.getValue(), null));
            }

            @Override
            public void accept(ConditionalOption option) {
                stateMap.put(option, new JaxbOption(option.getValue(), option.getCondition()));
            }

            @Override
            public void accept(Menu menu) {
                stateMap.put(menu, new JaxbMenu());
            }

            @Override
            public void accept(Switch aSwitch) {
                stateMap.put(aSwitch, new JaxbComplexSwitch());
            }

            @Override
            public void accept(EndPoint point) {
                stateMap.put(point, new JaxbEndState());
            }
        };
        FlowchartMouse mouse = new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
        flowchart.accept(mouse);
    }

    private void prepareSwitchBlocks(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(Switch aSwitch) {
                JaxbComplexSwitch jaxbComplexSwitch = (JaxbComplexSwitch) stateMap.get(aSwitch);

                jaxbComplexSwitch.addChild(new JaxbSwitchBranch(aSwitch.getCondition()));
                jaxbComplexSwitch.addChild(new JaxbSwitchBranch());
            }
        };
        FlowchartMouse mouse = new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
        flowchart.accept(mouse);
    }

    private void combineBlocks(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptComplexState((JaxbRootState) stateMap.get(state), state);
            }

            @Override
            public void accept(Option option) {
                acceptComplexState((JaxbOption) stateMap.get(option), option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptComplexState((JaxbOption) stateMap.get(option), option);
            }

            @Override
            public void accept(Menu menu) {
                JaxbMenu jaxbMenu = (JaxbMenu) stateMap.get(menu);

                menu.branchesStream().forEach(option ->
                        jaxbMenu.addChild(stateMap.get(option)));
            }

            @Override
            public void accept(Switch aSwitch) {
                JaxbComplexSwitch jaxbComplexSwitch = (JaxbComplexSwitch) stateMap.get(aSwitch);

                if (aSwitch.getTrueBranch() != null) {
                    JaxbSwitchBranch branch = (JaxbSwitchBranch) jaxbComplexSwitch.childAt(0);

                    branch.addChild(stateMap.get(aSwitch.getTrueBranch()));
                    acceptComplexState(branch, aSwitch.getTrueBranch());
                }

                if (aSwitch.getFalseBranch() != null) {
                    JaxbSwitchBranch branch = (JaxbSwitchBranch) jaxbComplexSwitch.childAt(1);

                    branch.addChild(stateMap.get(aSwitch.getFalseBranch()));
                    acceptComplexState(branch, aSwitch.getFalseBranch());
                }
            }

            private void acceptComplexState(JaxbComplexState complexState, State state) {
                if (!(state instanceof ChainState)) {
                    return;
                }

                State next = ((ChainState) state).getNext();

                while (next instanceof ChainState) {
                    complexState.addChild(stateMap.get(next));
                    next = ((ChainState) next).getNext();
                }

                complexState.addChild(stateMap.get(next));
            }
        };
        FlowchartMouse mouse = new FlowchartMouse(contract, new ParentFirstFlowchartMouseStrategy());
        flowchart.accept(mouse);
    }
}
