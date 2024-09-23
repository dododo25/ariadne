package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.SkipComplexState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;

public final class RenPyLineProcessorFactory {

    private RenPyLineProcessorFactory() {}

    public static LineProcessor create(Configuration configuration) {
        List<LineProcessor> processors = new ArrayList<>();

        populateInvalidLineProcessors(processors);
        populateSkipComplexStateLineProcessors(processors, configuration);
        populateProcessors(processors);

        if (configuration.isLoadReply()) {
            populateReplyRelatedProcessors(processors);
        }

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

    private static void populateSkipComplexStateLineProcessors(List<LineProcessor> processors,
                                                               Configuration configuration) {
        configuration.getExcluded()
                .forEach(line -> processors.add(createSkipComplexStateLineProcessor(line)));
    }

    private static void populateProcessors(List<LineProcessor> processors) {
        processors.add(new InitLineProcessor());
        processors.add(new CallLineProcessor());
        processors.add(new JumpLineProcessor());
        processors.add(new RenPyLabelLineProcessor());
        processors.add(new MenuLineProcessor());
        processors.add(new SingleLineCommentLineProcessor());
        processors.add(new ReplaceBracketsInTextLineProcessor(new ConditionalOptionLineProcessor()));
        processors.add(new ReplaceBracketsInTextLineProcessor(new OptionLineProcessor()));
        processors.add(new PassLineProcessor());
        processors.add(new StatementLineProcessor());
        processors.add(new SwitchIfLineProcessor());
        processors.add(new SwitchElseIfLineProcessor());
        processors.add(new SwitchElseLineProcessor());
        processors.add(new ReturnLineProcessor());
    }

    private static void populateReplyRelatedProcessors(List<LineProcessor> processors) {
        processors.add(new ReplaceBracketsInTextLineProcessor(new WithoutCharacterReplyLineProcessor()));
        processors.add(new ReplaceBracketsInTextLineProcessor(new WithValueCharacterReplyLineProcessor()));
        processors.add(new ReplaceBracketsInTextLineProcessor(new WithTextValueCharacterReplyLineProcessor()));
    }

    private static LineProcessor createSkipComplexStateLineProcessor(String regex) {
        return new GenericLineProcessor(regex) {
            @Override
            public State prepareState(Matcher matcher) {
                return new SkipComplexState();
            }
        };
    }
}
