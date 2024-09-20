package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;

import java.util.Collection;

public class LeafChainStateCollector extends StateCollector<ChainState> {

    public LeafChainStateCollector(FlowchartMouse mouse) {
        super(mouse);
    }

    @Override
    public void accept(Collection<ChainState> set, State state) {
        if (state instanceof ChainState && ((ChainState) state).getNext() == null) {
            set.add((ChainState) state);
        }
    }
}
