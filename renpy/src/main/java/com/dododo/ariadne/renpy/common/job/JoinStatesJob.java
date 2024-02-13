package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ChildFirstRenPyJaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

import java.util.HashMap;
import java.util.Map;

public final class JoinStatesJob extends AbstractJob {

    private final JaxbState rootState;

    private final StateCollector<ChainState> leafChainStateCollector;

    public JoinStatesJob(JaxbState rootState) {
        this.rootState = rootState;
        this.leafChainStateCollector = new LeafChainStateCollector(new ParentFirstRenPyFlowchartMouse());
    }

    @Override
    public void run() {
        Map<JaxbState, State> map = new HashMap<>();

        collectStates(map);
        joinStates(map);
        setFlowchart(map.get(rootState));
    }

    private void collectStates(Map<JaxbState, State> map) {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbRootState state) {
                map.put(state, new ComplexState());
            }

            @Override
            public void accept(JaxbInitGroupState state) {
                map.put(state, new ComplexState());
            }

            @Override
            public void accept(JaxbText text) {
                map.put(text, new Text(text.getValue()));
            }

            @Override
            public void accept(JaxbReply reply) {
                map.put(reply, new Reply(reply.getCharacter(), reply.getLine()));
            }

            @Override
            public void accept(JaxbRenPyMenu menu) {
                map.put(menu, new Menu(menu.getValue()));
            }

            @Override
            public void accept(JaxbOption option) {
                if (option.getCondition() == null) {
                    map.put(option, new Option(option.getValue()));
                } else {
                    map.put(option, new ConditionalOption(option.getValue(), option.getCondition()));
                }
            }

            @Override
            public void accept(JaxbComplexSwitch complexSwitch) {
                map.put(complexSwitch, new ComplexSwitch());
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                map.put(switchBranch, new SwitchBranch(switchBranch.getValue()));
            }

            @Override
            public void accept(JaxbSwitchFalseBranch switchBranch) {
                map.put(switchBranch, new SwitchBranch(switchBranch.getValue()));
            }

            @Override
            public void accept(JaxbLabelledGroup group) {
                map.put(group, new Label(group.getValue()));
            }

            @Override
            public void accept(JaxbGoToState state) {
                map.put(state, new GoToPoint(state.getValue()));
            }

            @Override
            public void accept(JaxbCallToState state) {
                map.put(state, new CallToState(state.getValue()));
            }

            @Override
            public void accept(JaxbPassState state) {
                map.put(state, new PassState());
            }

            @Override
            public void accept(JaxbEndState state) {
                map.put(state, new EndPoint());
            }
        };
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }

    private void joinStates(Map<JaxbState, State> map) {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbRootState state) {
               acceptComplexState(state);
            }

            @Override
            public void accept(JaxbInitGroupState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbComplexSwitch complexSwitch) {
                acceptComplexState(complexSwitch);
            }

            @Override
            public void accept(JaxbOption option) {
                acceptChainState(option);
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                acceptChainState(switchBranch);
            }

            @Override
            public void accept(JaxbSwitchFalseBranch switchBranch) {
                acceptChainState(switchBranch);
            }

            @Override
            public void accept(JaxbLabelledGroup group) {
                acceptChainState(group);
            }

            @Override
            public void accept(JaxbRenPyMenu jaxbMenu) {
                Menu menu = (Menu) map.get(jaxbMenu);

                jaxbMenu.childrenStream()
                        .map(map::get)
                        .map(Option.class::cast)
                        .forEach(menu::addBranch);
            }

            private void acceptComplexState(JaxbComplexState jaxbState) {
                ComplexState state = (ComplexState) map.get(jaxbState);

                if (jaxbState.childrenCount() == 0) {
                    state.addChild(new PassState());
                }

                jaxbState.childrenStream()
                        .map(map::get)
                        .forEach(state::addChild);
            }

            private void acceptChainState(JaxbComplexState jaxbState) {
                State lastState = map.get(jaxbState);

                if (jaxbState.childrenCount() == 0) {
                    ((ChainState) lastState).setNext(new PassState());
                }

                for (int i = 0; i < jaxbState.childrenCount(); i++) {
                    State child = map.get(jaxbState.childAt(i));

                    leafChainStateCollector.collect(lastState)
                            .forEach(leaf -> leaf.setNext(child));

                    lastState = child;
                }
            }
        };
        JaxbFlowchartMouse mouse = new ChildFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
