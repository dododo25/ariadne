package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.factory.ChildFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.ParentFirstRenPyLargeTreeFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.mouse.strategy.ChildFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveComplexStatesJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractFactory parentFirstFactory = selectFactoryBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory());

        FlowchartContractFactory childFirstFactory = selectFactoryBasedOnFlowchartTreeSize(
                new ChildFirstRenPyLargeTreeFlowchartContractFactory(),
                new RenPyFlowchartContractFactory(new ChildFirstRenPyFlowchartMouseStrategy())
        );

        StateCollector<ChainState> leafChainStateCollector = new LeafChainStateCollector(parentFirstFactory);

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

        State rootState = getFlowchart();

        childFirstFactory.process(rootState, callback);

        if (rootState instanceof ComplexState) {
            State newRootState = ((ComplexState) rootState).childAt(0);

            setFlowchart(newRootState);
            newRootState.removeRoot(rootState);
        }
    }
}
