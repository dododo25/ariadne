package com.dododo.ariadne.extended.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;

public final class PassState extends ChainState {

    @Override
    public void accept(FlowchartContract contract) {
        ((ExtendedFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareByClass(o);
    }
}
