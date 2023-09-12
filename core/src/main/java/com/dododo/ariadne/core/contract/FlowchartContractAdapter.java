package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;

public abstract class FlowchartContractAdapter implements FlowchartContract {

    @Override
    public void accept(EntryState state) {}

    @Override
    public void accept(Text text) {}

    @Override
    public void accept(Reply reply) {}

    @Override
    public void accept(Menu menu) {}

    @Override
    public void accept(Option option) {}

    @Override
    public void accept(ConditionalOption option) {}

    @Override
    public void accept(Switch aSwitch) {}

    @Override
    public void accept(EndPoint point) {}
}
