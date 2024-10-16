package com.dododo.ariadne.renpy.contract;

import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

public abstract class RenPyFlowchartContractAdapter extends ExtendedFlowchartContractAdapter
        implements RenPyFlowchartContract {

    @Override
    public void accept(CallToState callState) {}

    @Override
    public void accept(LabelledGroupComplexState group) {}

    @Override
    public void accept(VariableGroupComplexState group) {}
}
