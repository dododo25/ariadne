package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareGroupCallStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareGroupCallStatesJob.expected.xml") State expected,
                               @InputParam("PrepareGroupCallStatesJob.setup.xml") State setup) {
        PrepareGroupCallStatesJob job = new PrepareGroupCallStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}
