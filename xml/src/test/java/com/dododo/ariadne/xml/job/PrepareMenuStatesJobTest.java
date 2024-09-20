package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareMenuStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareMenuStatesJob.expected.xml") State expected,
                               @InputParam("PrepareMenuStatesJob.setup.xml") State setup) {
        PrepareMenuStatesJob job = new PrepareMenuStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ExtendedFlowchartMouse());
    }
}
