package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.extended.mouse.ParentFirstExtendedFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinLabelWithGoToPointsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinLabelWithGoToPointsJob.expected.xml") State expected,
                               @InputParam("JoinLabelWithGoToPointsJob.setup.xml") State setup) {
        JoinLabelWithGoToPointsJob job = new JoinLabelWithGoToPointsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ParentFirstExtendedFlowchartMouse());
    }
}
