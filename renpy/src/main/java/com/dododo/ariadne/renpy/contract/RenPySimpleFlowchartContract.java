package com.dododo.ariadne.renpy.contract;

import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

public abstract class RenPySimpleFlowchartContract extends ExtendedSimpleFlowchartContract
        implements RenPyFlowchartContract {

    @Override
    public void accept(RootComplexState complexState) {
        acceptState(complexState);
    }

    @Override
    public void accept(CallToState callState) {
        acceptState(callState);
    }

    @Override
    public void accept(LabelledGroupComplexState group) {
        acceptState(group);
    }

    @Override
    public void accept(VariableGroupComplexState group) {
        acceptState(group);
    }
}
