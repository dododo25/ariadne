package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EntryState;
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
        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {
            @Override
            public void accept(ComplexState state) {
                State child = state.childAt(0);

                for (int i = 0; i < state.childrenCount() - 1; i++) {
                    State nextChild = state.childAt(i + 1);

                    leafChainStateCollector.collect(child).forEach(leaf -> {
                        leaf.setNext(nextChild);
                        nextChild.removeRoot(state);
                    });

                    child = nextChild;
                }

                RenPyStateManipulatorUtil.replace(state, state.childAt(0));
            }
        };
        RenPyFlowchartMouse mouse = new RenPyFlowchartMouse(callback, new ChildFirstRenPyFlowchartMouseStrategy());

        getFlowchart().accept(mouse);

        EntryState newRoot = new EntryState();
        newRoot.setNext(((ComplexState) getFlowchart()).childAt(0));
        setFlowchart(newRoot);
    }
}
