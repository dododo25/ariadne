package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;

import java.util.function.Consumer;

public class ExtendedFlowchartMouse extends FlowchartMouse {

    @Override
    public void accept(State state, Consumer<State> consumer) {
        ExtendedFlowchartContract callback = new ExtendedSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    protected InnerFlowchartContract prepareInnerContract(FlowchartContract callback) {
        return new InnerExtendedFlowchartContract(callback);
    }

    protected static class InnerExtendedFlowchartContract extends InnerFlowchartContract implements ExtendedFlowchartContract {

        public InnerExtendedFlowchartContract(FlowchartContract callback) {
            super(callback);
        }

        @Override
        public void accept(ComplexState state) {
            acceptComplexState(state);
        }

        @Override
        public void accept(ComplexSwitch complexSwitch) {
            acceptComplexState(complexSwitch);
        }

        @Override
        public void accept(ComplexSwitchBranch branch) {
            acceptComplexState(branch);
        }

        @Override
        public void accept(Marker marker) {
            acceptChainState(marker);
        }

        @Override
        public void accept(PassState state) {
            acceptChainState(state);
        }

        @Override
        public void accept(ComplexMenu complexMenu) {
            acceptComplexState(complexMenu);
        }

        @Override
        public void accept(ComplexOption complexOption) {
            acceptComplexState(complexOption);
        }

        @Override
        public void accept(GoToPoint point) {
            point.accept(callback);
        }

        protected void acceptComplexState(ComplexState complexState) {
            complexState.accept(callback);

            complexState.childrenStream()
                    .forEach(grayStates::add);
        }
    }
}
