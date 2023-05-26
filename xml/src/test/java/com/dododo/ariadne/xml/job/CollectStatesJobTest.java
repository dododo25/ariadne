package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.test.assertions.JaxbAssertions;
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
    void testRunShouldDoneWell(@InputParam("CollectStatesJob.expected.xml") JaxbState expected) {
        URL url = CollectStatesJobTest.class.getResource("CollectStatesJob.setup.xml");

        if (url == null) {
            Assertions.fail();
        }

        String setupFile = url.getFile();
        JaxbRootState rootState = new JaxbRootState();

        CollectStatesJob job = new CollectStatesJob(rootState, 0);

        job.setConfiguration(createConfig(setupFile));
        job.run();

        JaxbAssertions.assertEquals(expected, rootState);
    }

    private Configuration createConfig(String setupFile) {
        Configuration config = mock(Configuration.class);

        when(config.getInputFiles())
                .thenReturn(Collections.singletonList(setupFile));

        return config;
    }
}
