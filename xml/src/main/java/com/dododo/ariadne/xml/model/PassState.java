package com.dododo.ariadne.xml.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.xml.contract.XmlFlowchartContract;

public final class PassState extends ChainState {

    @Override
    public void accept(FlowchartContract contract) {
        ((XmlFlowchartContract) contract).accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareByClass(o);
    }
}
