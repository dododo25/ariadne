package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

public final class EntryState extends ChainState {

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareByClass(o);
    }
}
