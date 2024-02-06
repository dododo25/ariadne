package com.dododo.ariadne.core.collector;

import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;

import java.util.Set;

public class LeafChainStateCollector extends StateCollector<ChainState> {

    public LeafChainStateCollector(FlowchartContractComposer composer) {
        super(composer);
    }

    @Override
    public void accept(Set<ChainState> set, State state) {
        if (state instanceof ChainState && ((ChainState) state).getNext() == null) {
            set.add((ChainState) state);
        }
    }
}
