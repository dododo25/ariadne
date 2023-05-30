package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSkipComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import com.dododo.ariadne.renpy.processor.LineToSkipProcessor;
import com.dododo.ariadne.renpy.processor.MenuLineProcessor;
import com.dododo.ariadne.renpy.processor.OptionLineProcessor;
import com.dododo.ariadne.renpy.processor.ReplaceBracketsInTextLineProcessor;
import com.dododo.ariadne.renpy.processor.ReturnLineProcessor;
import com.dododo.ariadne.renpy.processor.StatementLineProcessor;
import com.dododo.ariadne.renpy.processor.SwitchElseIfLineProcessor;
import com.dododo.ariadne.renpy.processor.SwitchElseLineProcessor;
import com.dododo.ariadne.renpy.processor.SwitchIfLineProcessor;
import com.dododo.ariadne.renpy.processor.WithTextValueCharacterReplyLineProcessor;
import com.dododo.ariadne.renpy.processor.WithValueCharacterReplyLineProcessor;
import com.dododo.ariadne.renpy.processor.WithoutCharacterReplyLineProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;

public final class LineProcessorFactory {

    private LineProcessorFactory() {}

    public static LineProcessor create(Configuration config) {
        List<LineProcessor> processors = new ArrayList<>();

        populateSkipComplexStateLineProcessors(processors, config);
        populateProcessors(processors);

        if (config.isLoadReply()) {
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

    private static void populateSkipComplexStateLineProcessors(List<LineProcessor> processors,
                                                               Configuration configuration) {
        configuration.getExcluded()
                .forEach(line -> processors.add(createSkipComplexStateLineProcessor(line)));
    }

    private static void populateProcessors(List<LineProcessor> processors) {
        processors.add(new GoToLineProcessor());
        processors.add(new LabelLineProcessor());
        processors.add(new MenuLineProcessor());
        processors.add(new ReplaceBracketsInTextLineProcessor(new RemoveTextMarkLineProcessor(
                new OptionLineProcessor())));
        processors.add(new StatementLineProcessor());
        processors.add(new RemoveStatementMarkLineProcessor(new SwitchIfLineProcessor()));
        processors.add(new RemoveStatementMarkLineProcessor(new SwitchElseIfLineProcessor()));
        processors.add(new RemoveStatementMarkLineProcessor(new SwitchElseLineProcessor()));
        processors.add(new ReturnLineProcessor());
    }

    private static void populateReplyRelatedProcessors(List<LineProcessor> processors) {
        processors.add(new ReplaceBracketsInTextLineProcessor(new RemoveTextMarkLineProcessor(
                new WithoutCharacterReplyLineProcessor())));
        processors.add(new ReplaceBracketsInTextLineProcessor(new RemoveTextMarkLineProcessor(
                new WithValueCharacterReplyLineProcessor())));
        processors.add(new ReplaceBracketsInTextLineProcessor(new RemoveTextMarkLineProcessor(
                new WithTextValueCharacterReplyLineProcessor())));
    }

    private static LineProcessor createSkipComplexStateLineProcessor(String regex) {
        return new GenericLineProcessor(regex) {
            @Override
            public JaxbState prepareState(Matcher matcher) {
                return new JaxbSkipComplexState();
            }
        };
    }
}
