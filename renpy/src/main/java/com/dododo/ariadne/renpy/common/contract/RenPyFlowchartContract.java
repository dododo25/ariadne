package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;

public interface RenPyFlowchartContract extends FlowchartContract {

    void accept(ComplexState state);

    void accept(PassState state);

    void accept(LabelledGroup group);

    void accept(CallToState callState);

    void accept(JumpToPoint point);

    void accept(ComplexSwitch complexSwitch);

    void accept(SwitchBranch branch);
}
