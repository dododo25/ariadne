package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveExcludedStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveExcludedStatesJob.expected.xml") State expected,
                               @InputParam("RemoveExcludedStatesJob.setup.xml") State setup) {
        Configuration config = mock(Configuration.class);

        when(config.getExcluded())
                .thenReturn(Collections.singleton("^\\$\\s+excluded\\s*=\\s*True$"));

        RemoveExcludedStatesJob job = new RemoveExcludedStatesJob();

        job.setConfiguration(config);
        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new XmlFlowchartMouse());
    }
}
