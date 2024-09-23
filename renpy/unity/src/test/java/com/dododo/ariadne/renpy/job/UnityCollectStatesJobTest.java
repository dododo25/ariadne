package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class UnityCollectStatesJobTest {

    @Test
    void testRun(@InputParam("UnityCollectStatesJob.expected.xml") State expected) {
        ComplexState rootState = new RootComplexState();

        List<String> input = Arrays.asList(
                "label x                ",
                "    if $expression1:   ",
                "        $ value = 1    ",
                "    elif $expression2: ",
                "        $ value = 2    ",
                "    else:              ",
                "        $ value = 3    ",
                // must be excluded
                "    12345              ",
                "        $ value = 4    ");

        UnityCollectStatesJob job = new UnityCollectStatesJob(input);

        job.setConfiguration(createConfig());
        job.setFlowchart(rootState);

        job.run();

        StateAssertions.assertEquals(expected, rootState, new RenPyFlowchartMouse());
    }

    private Configuration createConfig() {
        Configuration configuration = mock(Configuration.class);

        when(configuration.isLoadReply()).thenReturn(true);
        when(configuration.getExcluded())
                .thenReturn(Collections.singleton("^[0-9]+$"));

        return configuration;
    }
}
