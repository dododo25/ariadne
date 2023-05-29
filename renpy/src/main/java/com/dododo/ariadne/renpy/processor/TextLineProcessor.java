package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

public abstract class TextLineProcessor extends PreparedLineProcessor {

    protected TextLineProcessor(String regex) {
        super(regex);
    }

    @Override
    public JaxbState accept(String s) {
        String prepared = s.replace('"', '\'')
                .trim();

        return super.accept(prepared);
    }
}
