package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

public abstract class PreparedLineProcessor extends LineProcessor {

    protected PreparedLineProcessor(String regex) {
        super(regex);
    }

    @Override
    public JaxbState accept(String s) {
        return super.accept(s.replace('"', '\''));
    }
}
