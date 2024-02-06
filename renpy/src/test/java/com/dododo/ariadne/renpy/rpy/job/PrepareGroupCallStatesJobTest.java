package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.common.composer.RenPyFlowchartContractComposer;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareGroupCallStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareGroupCallStatesJob.expected.xml") State expected,
                               @InputParam("PrepareGroupCallStatesJob.setup.xml") State setup) {
        PrepareGroupCallStatesJob job = new PrepareGroupCallStatesJob();

        job.setConfiguration(Mockito.mock(Configuration.class));
        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartContractComposer());
    }
}
