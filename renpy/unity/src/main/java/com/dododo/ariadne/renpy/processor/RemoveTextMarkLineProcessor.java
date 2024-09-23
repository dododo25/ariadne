package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;

public final class RemoveTextMarkLineProcessor extends LineProcessor {

    private final LineProcessor processor;

    public RemoveTextMarkLineProcessor(LineProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected State prepareState(String s) {
        String prepared = s.replaceAll("^text\\d+:", "")
                .trim();

        return processor.accept(prepared);
    }
}
