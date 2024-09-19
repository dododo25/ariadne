package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URL;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class CollectStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("CollectStatesJob.expected.xml") State expected) {
        URL url = CollectStatesJobTest.class.getResource("CollectStatesJob.setup.xml");

        if (url == null) {
            Assertions.fail();
        }

        String setupFile = url.getFile();
        State rootState = new ComplexState();

        CollectStatesJob job = new CollectStatesJob(0);

        job.setConfiguration(createConfig(setupFile));
        job.setFlowchart(rootState);
        job.run();

        StateAssertions.assertEquals(expected, rootState, new XmlFlowchartMouse());
    }

    private Configuration createConfig(String setupFile) {
        Configuration config = mock(Configuration.class);

        when(config.getInputFiles())
                .thenReturn(Collections.singletonList(setupFile));

        return config;
    }
}
