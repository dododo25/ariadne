package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.util.XmlFlowchartManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector =
                new LeafChainStateCollector(new XmlFlowchartMouse());

        XmlFlowchartContract callback = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = 0; i < state.childrenCount() - 1; i++) {
                    State child = state.childAt(i);
                    State nextChild = state.childAt(i + 1);

                    leafChainStateCollector.collect(child)
                            .forEach(leaf -> leaf.setNext(nextChild));

                    nextChild.removeRoot(state);
                }

                XmlFlowchartManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        FlowchartMouse mouse = new XmlFlowchartMouse();

        State rootState = getFlowchart();
        State newRootState = rootState;

        mouse.accept(rootState, callback);

        while (newRootState instanceof ComplexState) {
            newRootState = ((ComplexState) newRootState).childAt(0);
        }

        setFlowchart(newRootState);
        newRootState.removeRoot(rootState);
    }
}
