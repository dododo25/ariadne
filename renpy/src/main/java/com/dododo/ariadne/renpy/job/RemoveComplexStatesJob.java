package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.util.RenPyStateManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector =
                new LeafChainStateCollector(new RenPyFlowchartMouse());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = 0; i < state.childrenCount() - 1; i++) {
                    State child = state.childAt(i);
                    State nextChild = state.childAt(i + 1);

                    leafChainStateCollector.collect(child)
                            .forEach(leaf -> leaf.setNext(nextChild));

                    state.removeChild(child);
                }

                RenPyStateManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

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
