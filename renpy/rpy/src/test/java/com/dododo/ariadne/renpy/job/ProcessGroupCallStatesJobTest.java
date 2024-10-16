package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class ProcessGroupCallStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("ProcessGroupCallStatesJob.expected.xml") State expected,
                               @InputParam("ProcessGroupCallStatesJob.setup.xml") State setup) {
        ProcessGroupCallStatesJob job = new ProcessGroupCallStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}
