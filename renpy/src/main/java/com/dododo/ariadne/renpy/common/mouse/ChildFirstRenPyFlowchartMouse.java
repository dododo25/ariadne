package com.dododo.ariadne.renpy.common.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.mouse.ChildFirstExtendedFlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstRenPyFlowchartMouse extends ChildFirstExtendedFlowchartMouse {

    @Override
    public void accept(State state, Consumer<State> consumer) {
        RenPyFlowchartContract callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    protected Collection<State> prepareStartingPoints(State state) {
        Collection<State> result = new HashSet<>();

        FlowchartContract callback = new RenPyInnerFlowchartContract(result);
        FlowchartMouse mouse = new ParentFirstRenPyFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    private static class RenPyInnerFlowchartContract extends ExtendedInnerFlowchartContract
            implements RenPyFlowchartContract {

        public RenPyInnerFlowchartContract(Collection<State> result) {
            super(result);
        }

        @Override
        public void accept(CallToState callState) {
            acceptChainState(callState);
        }
    }
}
