package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.extended.util.ExtendedFlowchartManipulatorUtil;
import com.dododo.ariadne.extended.model.ComplexState;

public final class RemoveComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector =
                new LeafChainStateCollector(new ExtendedFlowchartMouse());

        ExtendedFlowchartContract callback = new ExtendedFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = 0; i < state.childrenCount() - 1; i++) {
                    State child = state.childAt(i);
                    State nextChild = state.childAt(i + 1);

                    leafChainStateCollector.collect(child)
                            .forEach(leaf -> leaf.setNext(nextChild));

                    nextChild.removeRoot(state);
                }

                ExtendedFlowchartManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        FlowchartMouse mouse = new ExtendedFlowchartMouse();

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
