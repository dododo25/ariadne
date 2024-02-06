package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartContractFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinGoToPointsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinGoToPointsJob.expected.xml") State expected,
                               @InputParam("JoinGoToPointsJob.setup.xml") State setup) {
        JoinGoToPointsJob job = new JoinGoToPointsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartContractFactory());
    }
}
