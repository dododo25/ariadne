package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;

public abstract class SimpleFlowchartContract implements FlowchartContract {

    @Override
    public final void accept(EntryState state) {
        acceptState(state);
    }

    @Override
    public final void accept(Statement statement) {
        acceptState(statement);
    }

    @Override
    public final void accept(Switch aSwitch) {
        acceptState(aSwitch);
    }

    @Override
    public final void accept(EndPoint point) {
        acceptState(point);
    }

    public abstract void acceptState(State state);
}
