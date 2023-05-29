package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;

public abstract class RenPySimpleFlowchartContract extends SimpleFlowchartContract
        implements RenPyFlowchartContract {

    @Override
    public void accept(ComplexState state) {
        acceptState(state);
    }

    @Override
    public void accept(PassState state) {
        acceptState(state);
    }

    @Override
    public void accept(LabelledGroup group) {
        acceptState(group);
    }

    @Override
    public void accept(CallToState callState) {
        acceptState(callState);
    }

    @Override
    public void accept(JumpToPoint point) {
        acceptState(point);
    }

    @Override
    public void accept(ComplexSwitch complexSwitch) {
        acceptState(complexSwitch);
    }

    @Override
    public void accept(SwitchBranch branch) {
        acceptState(branch);
    }
}
