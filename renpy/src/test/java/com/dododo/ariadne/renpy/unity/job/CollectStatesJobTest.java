package com.dododo.ariadne.renpy.unity.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
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
class CollectStatesJobTest {

    @Test
    void testRun(@InputParam("CollectStatesJob.expected.xml") JaxbState expected) {
        JaxbGroupState rootState = new JaxbGroupState();

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

        CollectStatesJob job = new CollectStatesJob(rootState, input);

        job.setConfiguration(createConfig());
        job.run();

        JaxbAssertions.assertEquals(expected, rootState);
    }

    private Configuration createConfig() {
        Configuration configuration = mock(Configuration.class);

        when(configuration.isLoadReply()).thenReturn(true);
        when(configuration.getExcluded())
                .thenReturn(Collections.singleton("^[0-9]+$"));

        return configuration;
    }
}
