package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;

public abstract class RemoveComplexStatesJob extends AbstractJob {

    private final StateCollector<ChainState> leafChainStateCollector;

    public RemoveComplexStatesJob() {
        this.leafChainStateCollector = new LeafChainStateCollector(new LastChildRenPyFlowchartMouse());
    }

    protected void joinChildren(ComplexState complexState) {
        for (int i = 0; i < complexState.childrenCount() - 1; i++) {
            State child = complexState.childAt(i);
            State nextChild = complexState.childAt(i + 1);

            leafChainStateCollector.collect(child)
                    .forEach(leaf -> leaf.setNext(nextChild));

            nextChild.removeRoot(complexState);
        }
    }

    private static class LastChildRenPyFlowchartMouse extends RenPyFlowchartMouse {

        @Override
        protected InnerFlowchartContract prepareInnerContract(FlowchartContract callback) {
            return new LastChildRenPyFlowchartMouse.CustomFlowchartContract(callback);
        }

        private static class CustomFlowchartContract extends InnerRenPyFlowchartContract {

            public CustomFlowchartContract(FlowchartContract callback) {
                super(callback);
            }

            @Override
            public void accept(RootComplexState rootComplexState) {
                rootComplexState.accept(callback);
                rootComplexState.childrenStream()
                        .forEach(grayStates::add);
            }

            @Override
            public void accept(ComplexMenu complexMenu) {
                complexMenu.accept(callback);
                complexMenu.childrenStream()
                        .forEach(grayStates::add);
            }

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                complexSwitch.accept(callback);
                complexSwitch.childrenStream()
                        .forEach(grayStates::add);
            }

            @Override
            public void acceptComplexState(ComplexState complexState) {
                complexState.accept(callback);
                grayStates.add(complexState.childAt(complexState.childrenCount() - 1));
            }
        }
    }
}
