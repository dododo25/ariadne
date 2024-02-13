package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.extended.contract.ExtendedSimpleFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;

public abstract class RenPySimpleFlowchartContract extends ExtendedSimpleFlowchartContract
        implements RenPyFlowchartContract {

    @Override
    public void accept(CallToState callState) {
        acceptState(callState);
    }
}
