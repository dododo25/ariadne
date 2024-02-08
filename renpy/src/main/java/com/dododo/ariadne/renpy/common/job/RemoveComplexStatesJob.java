package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.common.mouse.ChildFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector =
                new LeafChainStateCollector(new ParentFirstRenPyFlowchartMouse());

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = state.childrenCount() - 1; i > 0; i--) {
                    State child = state.childAt(i);
                    State prevChild = state.childAt(i - 1);

                    leafChainStateCollector.collect(prevChild)
                            .forEach(leaf -> leaf.setNext(child));

                    state.removeChild(child);
                }

                RenPyStateManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        FlowchartMouse mouse = new ChildFirstRenPyFlowchartMouse();

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
