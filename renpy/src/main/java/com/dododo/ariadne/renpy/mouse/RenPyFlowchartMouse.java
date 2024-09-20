package com.dododo.ariadne.renpy.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;

import java.util.function.Consumer;

public class RenPyFlowchartMouse extends ExtendedFlowchartMouse {

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
    protected InnerFlowchartContract prepareInnerContract(FlowchartContract callback) {
        return new InnerRenPyFlowchartContract(callback);
    }

    private static class InnerRenPyFlowchartContract extends InnerExtendedFlowchartContract
            implements RenPyFlowchartContract {

        public InnerRenPyFlowchartContract(FlowchartContract callback) {
            super(callback);
        }

        @Override
        public void accept(CallToState callState) {
            acceptChainState(callState);
        }

        @Override
        public void accept(LabelledGroupComplexState group) {
            acceptComplexState(group);
        }

        @Override
        public void accept(VariableGroupComplexState group) {
            acceptComplexState(group);
        }
    }
}
