package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.composer.ChildFirstRenPyLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.composer.ParentFirstRenPyLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.mouse.strategy.ChildFirstRenPyFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.util.RenPyStateManipulatorUtil;

public final class RemoveComplexStatesJob extends RenPyAbstractJob {

    @Override
    public void run() {
        FlowchartContractComposer parentFirstComposer = selectComposerBasedOnFlowchartTreeSize(
                new ParentFirstRenPyLargeTreeFlowchartContractComposer(),
                new RenPyFlowchartContractComposer());

        FlowchartContractComposer childFirstComposer = selectComposerBasedOnFlowchartTreeSize(
                new ChildFirstRenPyLargeTreeFlowchartContractComposer(),
                new RenPyFlowchartContractComposer(new ChildFirstRenPyFlowchartMouseStrategy())
        );

        StateCollector<ChainState> leafChainStateCollector = new LeafChainStateCollector(parentFirstComposer);

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

        childFirstComposer.process(rootState, callback);

        if (rootState instanceof ComplexState) {
            State newRootState = ((ComplexState) rootState).childAt(0);

            setFlowchart(newRootState);
            newRootState.removeRoot(rootState);
        }
    }
}
