package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ChildFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveComplexStatesJob extends AbstractJob {

    private final StateCollector<ChainState> leafChainStateCollector;

    public RemoveComplexStatesJob() {
        leafChainStateCollector = new LeafChainStateCollector(new RenPyFlowchartMouseFactory());
    }

    @Override
    public void run() {
        State rootState = getFlowchart();

        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                for (int i = state.childrenCount() - 1; i > 0; i--) {
                    State child = state.childAt(i);
                    State prevChild = state.childAt(i - 1);

                    leafChainStateCollector.collect(prevChild)
                            .forEach(leaf -> leaf.setNext(child));

                    child.removeRoot(state);
                }

                RenPyStateManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        RenPyFlowchartMouse mouse = new RenPyFlowchartMouse(callback, new ChildFirstRenPyFlowchartMouseStrategy());

        rootState.accept(mouse);

        if (rootState instanceof ComplexState) {
            State newRootState = ((ComplexState) rootState).childAt(0);

            setFlowchart(newRootState);
            newRootState.removeRoot(rootState);
        }
    }
}
