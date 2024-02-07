package com.dododo.ariadne.xml.common.mouse;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.mouse.ChildFirstFlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlSimpleFlowchartContract;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;
import com.dododo.ariadne.xml.common.mouse.strategy.ChildFirstXmlFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstXmlFlowchartMouse extends ChildFirstFlowchartMouse {

    public ChildFirstXmlFlowchartMouse() {
        super(new ChildFirstXmlFlowchartMouseStrategy());
    }

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
    protected Collection<State> prepareStartingPoints(State state) {
        Collection<State> result = new HashSet<>();

        FlowchartContract callback = new XmlInnerFlowchartContract(result);
        FlowchartMouse mouse = new ParentFirstXmlFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    private static class XmlInnerFlowchartContract extends InnerFlowchartContract implements XmlFlowchartContract {

        public XmlInnerFlowchartContract(Collection<State> result) {
            super(result);
        }

        @Override
        public void accept(ComplexState state) {
            if (state.childrenCount() == 0) {
                result.add(state);
            }

            state.childrenStream()
                    .filter(ChainState.class::isInstance)
                    .map(ChainState.class::cast)
                    .forEach(child -> {
                        State nextState = child.getNext();

                        if (nextState == state) {
                            result.add(child);
                        }
                    });
        }

        @Override
        public void accept(ComplexSwitch complexSwitch) {
            accept((ComplexState) complexSwitch);
        }

        @Override
        public void accept(SwitchBranch branch) {
            acceptChainState(branch);
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
        public void accept(GoToPoint point) {
            result.add(point);
        }
    }
}
