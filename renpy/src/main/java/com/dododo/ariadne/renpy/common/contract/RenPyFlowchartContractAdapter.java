package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.model.CallToState;

public abstract class RenPyFlowchartContractAdapter extends ExtendedFlowchartContractAdapter
        implements RenPyFlowchartContract {

    @Override
    public void accept(CallToState callState) {}
}
