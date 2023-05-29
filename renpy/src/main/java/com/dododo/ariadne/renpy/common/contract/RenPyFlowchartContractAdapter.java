package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;

public abstract class RenPyFlowchartContractAdapter extends FlowchartContractAdapter
        implements RenPyFlowchartContract {

    @Override
    public void accept(ComplexState state) {}

    @Override
    public void accept(PassState state) {}

    @Override
    public void accept(LabelledGroup group) {}

    @Override
    public void accept(CallToState callState) {}

    @Override
    public void accept(JumpToPoint point) {}

    @Override
    public void accept(ComplexSwitch complexSwitch) {}

    @Override
    public void accept(SwitchBranch branch) {}
}
