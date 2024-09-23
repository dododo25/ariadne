package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;

public abstract class LineProcessor {
    protected LineProcessor next;

    public void setNext(LineProcessor next) {
        this.next = next;
    }

    public State accept(String s) {
        State state = prepareState(s);

        if (state != null) {
            return state;
        }

        return next == null ? null : next.accept(s);
    }

    protected abstract State prepareState(String s);
}
