package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinLabelWithJumpToPointsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinLabelWithGoToPointsJob.expected.xml") State expected,
                               @InputParam("JoinLabelWithGoToPointsJob.setup.xml") State setup) {
        JoinLabelWithJumpToPointsJob job = new JoinLabelWithJumpToPointsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}
