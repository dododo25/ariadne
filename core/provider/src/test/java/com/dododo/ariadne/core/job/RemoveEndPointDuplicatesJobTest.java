package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveEndPointDuplicatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveEndPointDuplicatesJob.expected.xml") State expected,
                               @InputParam("RemoveEndPointDuplicatesJob.setup.xml") State setup) {
        RemoveEndPointDuplicatesJob job = new RemoveEndPointDuplicatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new FlowchartMouse());
    }
}
