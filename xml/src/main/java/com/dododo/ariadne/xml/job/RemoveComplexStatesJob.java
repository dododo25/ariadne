package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartContractFactory;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ChildFirstXmlFlowchartMouseStrategy;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    private final StateCollector<ChainState> leafChainStateCollector;

    public RemoveComplexStatesJob() {
        leafChainStateCollector = new LeafChainStateCollector(new XmlFlowchartContractFactory());
    }

    @Override
    public void run() {
        State rootState = getFlowchart();

        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = state.childrenCount() - 1; i > 0; i--) {
                    State child = state.childAt(i);
                    State prevChild = state.childAt(i - 1);

                    leafChainStateCollector.collect(prevChild)
                            .forEach(leaf -> leaf.setNext(child));

                    child.removeRoot(state);
                }

                XmlStateManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        XmlFlowchartMouse mouse = new XmlFlowchartMouse(callback, new ChildFirstXmlFlowchartMouseStrategy());

        rootState.accept(mouse);

        if (rootState instanceof ComplexState) {
            State newRootState = ((ComplexState) rootState).childAt(0);

            setFlowchart(newRootState);
            newRootState.removeRoot(rootState);
        }
    }
}
