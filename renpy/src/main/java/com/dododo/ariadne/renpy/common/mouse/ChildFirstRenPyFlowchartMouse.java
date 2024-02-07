package com.dododo.ariadne.renpy.common.mouse;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.mouse.ChildFirstFlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.renpy.common.mouse.strategy.ChildFirstRenPyFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class ChildFirstRenPyFlowchartMouse extends ChildFirstFlowchartMouse {

    public ChildFirstRenPyFlowchartMouse() {
        super(new ChildFirstRenPyFlowchartMouseStrategy());
    }

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

    private static class RenPyInnerFlowchartContract extends InnerFlowchartContract implements RenPyFlowchartContract {

        public RenPyInnerFlowchartContract(Collection<State> result) {
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
        public void accept(PassState state) {
            acceptChainState(state);
        }

        @Override
        public void accept(LabelledGroup group) {
            acceptChainState(group);
        }

        @Override
        public void accept(CallToState callState) {
            acceptChainState(callState);
        }

        @Override
        public void accept(JumpToPoint point) {
            result.add(point);
        }
    }
}
