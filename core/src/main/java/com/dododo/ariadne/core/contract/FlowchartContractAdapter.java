package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;

public abstract class FlowchartContractAdapter implements FlowchartContract {

    @Override
    public void accept(EntryState state) {}

    @Override
    public void accept(Statement statement) {}

    @Override
    public void accept(Switch aSwitch) {}

    @Override
    public void accept(EndPoint point) {}
}
