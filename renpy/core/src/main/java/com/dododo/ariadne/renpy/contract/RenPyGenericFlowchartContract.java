package com.dododo.ariadne.renpy.contract;

import com.dododo.ariadne.extended.contract.ExtendedGenericFlowchartContract;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;

public class RenPyGenericFlowchartContract extends ExtendedGenericFlowchartContract
        implements RenPyFlowchartContract {

    @Override
    public final void accept(LabelledGroupComplexState group) {
        acceptComplexState(group);
    }

    @Override
    public final void accept(VariableGroupComplexState group) {
        acceptComplexState(group);
    }

    @Override
    public final void accept(CallToState callState) {
        acceptChainState(callState);
    }
}
