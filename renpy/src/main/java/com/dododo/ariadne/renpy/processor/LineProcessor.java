package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;

public abstract class LineProcessor {
    protected LineProcessor next;

    public void setNext(LineProcessor next) {
        this.next = next;
    }

    public JaxbState accept(String s) {
        JaxbState state = prepareState(s);

        if (state != null) {
            return state;
        }

        return next == null ? null : next.accept(s);
    }

    protected abstract JaxbState prepareState(String s);
}
