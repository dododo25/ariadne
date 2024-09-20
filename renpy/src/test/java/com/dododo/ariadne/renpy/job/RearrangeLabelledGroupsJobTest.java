package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RearrangeLabelledGroupsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RearrangeLabelledGroupsJob.expected.xml") State expected,
                               @InputParam("RearrangeLabelledGroupsJob.setup.xml") State setup) {
        RearrangeLabelledGroupsJob job = new RearrangeLabelledGroupsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new RenPyFlowchartMouse());
    }
}
