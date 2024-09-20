package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinMarkerWithGoToPointsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinMarkerWithGoToPointsJob.expected.xml") State expected,
                               @InputParam("JoinMarkerWithGoToPointsJob.setup.xml") State setup) {
        JoinMarkerWithGoToPointsJob job = new JoinMarkerWithGoToPointsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ExtendedFlowchartMouse());
    }
}
