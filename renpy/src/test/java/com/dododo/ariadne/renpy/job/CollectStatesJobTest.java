package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.test.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class CollectStatesJobTest {

    @Test
    void testRunWhenLoadReplyIsTrueShouldDoneWell(@InputParam("CollectStatesJob.expected1.xml") JaxbState expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.isLoadReply())
                .thenReturn(true);

        testRunShouldDoneWell(expected, config);
    }

    @Test
    void testRunWhenLoadReplyIsFalseShouldDoneWell(@InputParam("CollectStatesJob.expected2.xml") JaxbState expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.isLoadReply())
                .thenReturn(false);

        testRunShouldDoneWell(expected, config);
    }

    @Test
    void testRunWhenExcludedExistsShouldDoneWell(@InputParam("CollectStatesJob.expected3.xml") JaxbState expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.getExcluded())
                .thenReturn(Collections.singleton("\\$\\s+excluded\\s*=.*"));

        testRunShouldDoneWell(expected, config);
    }

    private void testRunShouldDoneWell(JaxbState expected, Configuration config) {
        JaxbComplexState rootState = new JaxbGroupState();

        for (int i = 0; i < config.getInputFiles().size(); i++) {
            CollectStatesJob job = new CollectStatesJob(i, rootState);

            job.setConfiguration(config);
            job.run();
        }

        JaxbAssertions.assertEquals(expected, rootState);
    }

    private Configuration createConfig() throws URISyntaxException {
        String path1 = Paths.get(CollectStatesJobTest.class.getResource("./CollectStatesJob.setup1.txt").toURI())
                .toString();
        String path2 = Paths.get(CollectStatesJobTest.class.getResource("./CollectStatesJob.setup2.txt").toURI())
                .toString();

        Configuration config = mock(Configuration.class);

        when(config.getInputFiles())
                .thenReturn(Arrays.asList(path1, path2));

        return config;
    }
}
