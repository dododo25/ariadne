package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.ChildFirstXmlFlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.mouse.ParentFirstXmlFlowchartMouse;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.util.XmlStateManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector =
                new LeafChainStateCollector(new ParentFirstXmlFlowchartMouse());

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
        FlowchartMouse mouse = new ChildFirstXmlFlowchartMouse();

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
