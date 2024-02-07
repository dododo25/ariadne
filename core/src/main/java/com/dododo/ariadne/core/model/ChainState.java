package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;

import java.util.Collection;

public abstract class ChainState extends State {

    private State next;

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        if (this.next != null) {
            this.next.removeRoot(this);
        }

        this.next = next;

        if (this.next != null) {
            this.next.addRoot(this);
        }
    }

    @Override
    public final void accept(FlowchartMouseStrategy strategy, FlowchartContract callback, Collection<State> grayStates, Collection<State> blackStates) {
        strategy.acceptChainState(this, callback, grayStates, blackStates);
    }
}
