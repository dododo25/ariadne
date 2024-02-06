package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareSwitchStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareSwitchStatesJob.expected.xml") State expected,
                               @InputParam("PrepareSwitchStatesJob.setup.xml") State setup) {
        PrepareSwitchStatesJob job = new PrepareSwitchStatesJob();

        job.setConfiguration(Mockito.mock(Configuration.class));
        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartContractComposer());
    }
}
