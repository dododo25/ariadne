package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.extended.model.RootComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
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
class RenPyCollectStatesJobTest {

    @Test
    void testRunWhenLoadReplyIsTrueShouldDoneWell(@InputParam("RenPyCollectStatesJob.expected1.xml") State expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.isLoadReply())
                .thenReturn(true);

        testRunShouldDoneWell(expected, config);
    }

    @Test
    void testRunWhenLoadReplyIsFalseShouldDoneWell(@InputParam("RenPyCollectStatesJob.expected2.xml") State expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.isLoadReply())
                .thenReturn(false);

        testRunShouldDoneWell(expected, config);
    }

    @Test
    void testRunWhenExcludedExistsShouldDoneWell(@InputParam("RenPyCollectStatesJob.expected3.xml") State expected) throws URISyntaxException {
        Configuration config = createConfig();

        when(config.getExcluded())
                .thenReturn(Collections.singleton("\\$\\s+excluded\\s*=.*"));

        testRunShouldDoneWell(expected, config);
    }

    private void testRunShouldDoneWell(State expected, Configuration config) {
        State rootState = new RootComplexState();

        for (int i = 0; i < config.getInputFiles().size(); i++) {
            RenPyCollectStatesJob job = new RenPyCollectStatesJob(i);

            job.setConfiguration(config);
            job.setFlowchart(rootState);

            job.run();
        }

        StateAssertions.assertEquals(expected, rootState, new RenPyFlowchartMouse());
    }

    private Configuration createConfig() throws URISyntaxException {
        String path1 = Paths.get(RenPyCollectStatesJobTest.class.getResource("./RenPyCollectStatesJob.setup1.txt").toURI())
                .toString();
        String path2 = Paths.get(RenPyCollectStatesJobTest.class.getResource("./RenPyCollectStatesJob.setup2.txt").toURI())
                .toString();

        Configuration config = mock(Configuration.class);

        when(config.getInputFiles())
                .thenReturn(Arrays.asList(path1, path2));

        return config;
    }
}
