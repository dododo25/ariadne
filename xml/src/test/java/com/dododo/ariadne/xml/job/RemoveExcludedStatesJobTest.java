package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.test.assertions.JaxbAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveExcludedStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveExcludedStatesJob.expected.xml") JaxbState expected,
                               @InputParam("RemoveExcludedStatesJob.setup.xml") JaxbState setup) {
        Configuration config = mock(Configuration.class);

        when(config.getExcluded())
                .thenReturn(Collections.singleton("^\\$\\s+excluded\\s*=\\s*True$"));

        RemoveExcludedStatesJob job = new RemoveExcludedStatesJob(setup);

        job.setConfiguration(config);
        job.run();

        JaxbAssertions.assertEquals(expected, setup);
    }
}
