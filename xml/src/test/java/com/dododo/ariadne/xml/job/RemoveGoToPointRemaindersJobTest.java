package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveGoToPointRemaindersJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveGoToPointRemaindersJob.expected.xml") State expected,
                               @InputParam("RemoveGoToPointRemaindersJob.setup.xml") State setup) {
        RemoveGoToPointRemaindersJob job = new RemoveGoToPointRemaindersJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartMouse());
    }
}
