package com.dododo.ariadne.renpy.common.mouse;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPySimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.renpy.common.mouse.strategy.RenPyFlowchartMouseStrategy;

public final class RenPyFlowchartMouse extends FlowchartMouse implements RenPyFlowchartContract {

    public RenPyFlowchartMouse(RenPyFlowchartMouseStrategy strategy) {
        super(strategy);

        this.callback = new RenPySimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // no processing
            }
        };
    }

    public RenPyFlowchartMouse(RenPyFlowchartContract callback, RenPyFlowchartMouseStrategy strategy) {
        super(callback, strategy);
    }

    @Override
    public void accept(ComplexState state) {
        ((RenPyFlowchartMouseStrategy) strategy).acceptComplexState(state, this, callback, visited);
    }

    @Override
    public void accept(ComplexSwitch complexSwitch) {
        ((RenPyFlowchartMouseStrategy) strategy).acceptComplexState(complexSwitch, this, callback, visited);
    }

    @Override
    public void accept(PassState state) {
        strategy.acceptChainState(state, this, callback, visited);
    }

    @Override
    public void accept(LabelledGroup group) {
        strategy.acceptChainState(group, this, callback, visited);
    }

    @Override
    public void accept(CallToState callState) {
        strategy.acceptChainState(callState, this, callback, visited);
    }

    @Override
    public void accept(SwitchBranch branch) {
        strategy.acceptChainState(branch, this, callback, visited);
    }

    @Override
    public void accept(JumpToPoint point) {
        acceptEndState(point);
    }
}
