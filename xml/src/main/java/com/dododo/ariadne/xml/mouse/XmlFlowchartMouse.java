package com.dododo.ariadne.xml.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

import java.util.function.Consumer;

public class XmlFlowchartMouse extends FlowchartMouse {

    @Override
    public void accept(State state, Consumer<State> consumer) {
        XmlFlowchartContract callback = new XmlSimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    protected InnerFlowchartContract prepareInnerContract(FlowchartContract callback) {
        return new InnerXmlFlowchartContract(callback);
    }

    protected static class InnerXmlFlowchartContract extends InnerFlowchartContract implements XmlFlowchartContract {

        public InnerXmlFlowchartContract(FlowchartContract callback) {
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

        private void acceptComplexState(ComplexState complexState) {
            complexState.accept(callback);

            complexState.childrenStream()
                    .forEach(grayStates::add);
        }
    }
}
