package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;

public interface FlowchartContract {

    void accept(EntryState state);

    void accept(Statement statement);

    void accept(Switch aSwitch);

    void accept(EndPoint point);
}
