package com.dododo.ariadne.extended.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.ChildFirstFlowchartMouse;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import com.dododo.ariadne.extended.mouse.strategy.ChildFirstExtendedFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstExtendedFlowchartMouse extends ChildFirstFlowchartMouse {

    public ChildFirstExtendedFlowchartMouse() {
        super(new ChildFirstExtendedFlowchartMouseStrategy());
    }

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
    protected Collection<State> prepareStartingPoints(State state) {
        Collection<State> result = new HashSet<>();

        FlowchartContract callback = new ExtendedInnerFlowchartContract(result);
        FlowchartMouse mouse = new ParentFirstExtendedFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    protected static class ExtendedInnerFlowchartContract extends InnerFlowchartContract
            implements ExtendedFlowchartContract {

        public ExtendedInnerFlowchartContract(Collection<State> result) {
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
        public void accept(Label group) {
            acceptChainState(group);
        }

        @Override
        public void accept(PassState state) {
            acceptChainState(state);
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
        public void accept(GoToPoint point) {
            result.add(point);
        }
    }
}
