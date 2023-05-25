package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.xml.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.xml.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

import java.util.HashMap;
import java.util.Map;

public final class JoinStatesJob extends AbstractJob {

    private final JaxbState rootState;

    private final StateCollector<ChainState> leafChainStateCollector;

    public JoinStatesJob(JaxbState rootState) {
        this.rootState = rootState;
        this.leafChainStateCollector = new LeafChainStateCollector(new XmlFlowchartMouseFactory());
    }

    @Override
    public void run() {
        Map<JaxbState, State> map = new HashMap<>();

        collectStates(map);
        joinStates(map);
        setFlowchart(map.get(rootState));
    }

    private void collectStates(Map<JaxbState, State> map) {
        JaxbFlowchartContract callback = new JaxbFlowchartContract() {
            @Override
            public void accept(JaxbRootState state) {
                map.put(state, new EntryState());
            }

            @Override
            public void accept(JaxbStatement statement) {
                map.put(statement, new Statement(statement.getValue()));
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
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }

    private void joinStates(Map<JaxbState, State> map) {
        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbRootState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                acceptChainState(switchBranch);
            }

            @Override
            public void accept(JaxbComplexSwitch complexSwitch) {
                ComplexState state = (ComplexState) map.get(complexSwitch);

                complexSwitch.childrenStream()
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
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ChildFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
