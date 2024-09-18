package com.dododo.ariadne.core.model;

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
}
