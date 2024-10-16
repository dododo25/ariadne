package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;

public abstract class GenericFlowchartContract implements FlowchartContract {

    @Override
    public final void accept(EntryState state) {
        acceptChainState(state);
    }

    @Override
    public final void accept(Text text) {
        acceptChainState(text);
    }

    @Override
    public final void accept(Reply reply) {
        acceptChainState(reply);
    }

    @Override
    public final void accept(Option option) {
        acceptChainState(option);
    }

    @Override
    public final void accept(ConditionalOption option) {
        acceptChainState(option);
    }

    @Override
    public final void accept(CycleMarker marker) {
        acceptChainState(marker);
    }

    @Override
    public final void accept(CycleEntryState entryState) {
        acceptChainState(entryState);
    }

    @Override
    public void accept(Menu menu) {}

    @Override
    public void accept(Switch aSwitch) {}

    @Override
    public void accept(EndPoint point) {}

    public void acceptChainState(ChainState state) {}
}
