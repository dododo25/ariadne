package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;

public final class RemoveTextMarkLineProcessor extends LineProcessor {

    private final LineProcessor processor;

    public RemoveTextMarkLineProcessor(LineProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected JaxbState prepareState(String s) {
        String prepared = s.replaceAll("^text\\d+:", "")
                .trim();

        return processor.accept(prepared);
    }
}
