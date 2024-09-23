package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareLinesJobTest {

    @Test
    void testRun() {
        List<String> expected = Arrays.asList(
                "if expression :",
                "elif expression :",
                "else :",
                "$ value = 1 ",
                "",
                "if expression:");

        List<String> input = new ArrayList<>(Arrays.asList(
                "if expression {",
                "elif expression {",
                "else {",
                "$ value = 1 }",
                "}}",
                "if expression",
                "{"));

        PrepareLinesJob job = new PrepareLinesJob(input);

        job.run();

        Assertions.assertEquals(expected.size(), input.size());
        IntStream.range(0, input.size())
                .forEach(index -> Assertions.assertEquals(expected.get(index), input.get(index)));
    }
}
