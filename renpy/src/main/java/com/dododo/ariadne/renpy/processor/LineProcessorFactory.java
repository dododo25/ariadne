package com.dododo.ariadne.renpy.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class LineProcessorFactory {

    private LineProcessorFactory() {}

    public static LineProcessor create() {
        List<LineProcessor> processors = new ArrayList<>();

        populateInvalidLineProcessors(processors);
        populateProcessors(processors);

        processors.add(new LineToSkipProcessor());

        AtomicReference<LineProcessor> lastProcessor = new AtomicReference<>(processors.get(0));

        processors.subList(1, processors.size()).forEach(lineProcessor -> {
            lastProcessor.get().setNext(lineProcessor);
            lastProcessor.set(lineProcessor);
        });

        return processors.get(0);
    }

    private static void populateInvalidLineProcessors(List<LineProcessor> processors) {
        processors.add(InvalidLineProcessor.INIT_PYTHON_LINE_PROCESSOR);
        processors.add(InvalidLineProcessor.CALL_EXPRESSION_LINE_PROCESSOR);
        processors.add(InvalidLineProcessor.RENPY_CALL_LINE_PROCESSOR);
    }

    private static void populateProcessors(List<LineProcessor> processors) {
        processors.add(new InitLineProcessor());
        processors.add(new CallLineProcessor());
        processors.add(new JumpLineProcessor());
        processors.add(new LabelLineProcessor());
        processors.add(new MenuLineProcessor());
        processors.add(new ConditionalOptionLineProcessor());
        processors.add(new OptionLineProcessor());
        processors.add(new PassLineProcessor());
        processors.add(new StatementLineProcessor());
        processors.add(new SwitchIfLineProcessor());
        processors.add(new SwitchElseIfLineProcessor());
        processors.add(new SwitchElseLineProcessor());
        processors.add(new ReturnLineProcessor());
        processors.add(new WithoutCharacterReplyLineProcessor());
        processors.add(new WithValueCharacterReplyLineProcessor());
        processors.add(new WithTextValueCharacterReplyLineProcessor());
    }
}
