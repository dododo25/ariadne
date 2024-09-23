package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;

public final class ReplaceBracketsInTextLineProcessor extends LineProcessor {

    private final LineProcessor processor;

    public ReplaceBracketsInTextLineProcessor(LineProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected State prepareState(String s) {
        String prepared = s.replace('"', '\'')
                .trim();

        return processor.accept(prepared);
    }
}
