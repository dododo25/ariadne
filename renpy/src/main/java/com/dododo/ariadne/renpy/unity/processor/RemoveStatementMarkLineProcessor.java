package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;

public final class RemoveStatementMarkLineProcessor extends LineProcessor {

    private final LineProcessor processor;

    public RemoveStatementMarkLineProcessor(LineProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected JaxbState prepareState(String s) {
        String prepared = s.replace("$", "")
                .trim();

        return processor.accept(prepared);
    }
}
