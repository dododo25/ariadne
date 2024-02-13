package com.dododo.ariadne.renpy.common.contract;

import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.renpy.common.model.CallToState;

public interface RenPyFlowchartContract extends ExtendedFlowchartContract {

    void accept(CallToState callState);
}
