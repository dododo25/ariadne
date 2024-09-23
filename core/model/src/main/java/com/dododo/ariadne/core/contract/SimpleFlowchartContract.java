package com.dododo.ariadne.core.contract;

import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;

public abstract class SimpleFlowchartContract implements FlowchartContract {

    @Override
    public final void accept(EntryState state) {
        acceptState(state);
    }

    @Override
    public final void accept(Text text) {
        acceptState(text);
    }

    @Override
    public final void accept(Reply reply) {
        acceptState(reply);
    }

    @Override
    public final void accept(Menu menu) {
        acceptState(menu);
    }

    @Override
    public final void accept(Option option) {
        acceptState(option);
    }

    @Override
    public final void accept(ConditionalOption option) {
        acceptState(option);
    }

    @Override
    public final void accept(Switch aSwitch) {
        acceptState(aSwitch);
    }

    @Override
    public final void accept(EndPoint point) {
        acceptState(point);
    }

    @Override
    public final void accept(CycleMarker marker) {
        acceptState(marker);
    }

    @Override
    public final void accept(CycleEntryState entryState) {
        acceptState(entryState);
    }

    public abstract void acceptState(State state);
}
