package com.dododo.ariadne.xml.job;

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
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.mouse.ChildFirstJaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.ParentFirstXmlFlowchartMouse;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbMarker;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.mouse.ParentFirstJaxbFlowchartMouse;

import java.util.HashMap;
import java.util.Map;

public final class JoinStatesJob extends AbstractJob {

    private final JaxbState rootState;

    private final StateCollector<ChainState> leafChainStateCollector;

    public JoinStatesJob(JaxbState rootState) {
        this.rootState = rootState;
        this.leafChainStateCollector = new LeafChainStateCollector(new ParentFirstXmlFlowchartMouse());
    }

    @Override
    public void run() {
        Map<JaxbState, State> map = new HashMap<>();

        collectStates(map);
        joinStates(map);
        setFlowchart(map.get(rootState));
    }

    private void collectStates(Map<JaxbState, State> map) {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbRootState state) {
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
            public void accept(JaxbMenu menu) {
                map.put(menu, new Menu());
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
            public void accept(JaxbMarker marker) {
                map.put(marker, new Marker(marker.getValue()));
            }

            @Override
            public void accept(JaxbGoToState state) {
                map.put(state, new GoToPoint(state.getValue()));
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
        JaxbFlowchartMouse mouse = new ParentFirstJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }

    private void joinStates(Map<JaxbState, State> map) {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbRootState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbMenu menu) {
                Menu menuState = (Menu) map.get(menu);

                menu.childrenStream()
                        .map(map::get)
                        .map(Option.class::cast)
                        .forEach(menuState::addBranch);
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

            private void acceptComplexState(JaxbComplexState jaxbState) {
                ComplexState state = (ComplexState) map.get(jaxbState);

                jaxbState.childrenStream()
                        .map(map::get)
                        .forEach(state::addChild);
            }

            private void acceptChainState(JaxbComplexState jaxbState) {
                State lastState = map.get(jaxbState);

                for (int i = 0; i < jaxbState.childrenCount(); i++) {
                    State child = map.get(jaxbState.childAt(i));

                    leafChainStateCollector.collect(lastState)
                            .forEach(leaf -> leaf.setNext(child));

                    lastState = child;
                }
            }
        };
        JaxbFlowchartMouse mouse = new ChildFirstJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
