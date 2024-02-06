package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartContractFactory;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinLinkJumpPointsJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinLinkJumpPointsJob.expected.xml") State expected,
                               @InputParam("JoinLinkJumpPointsJob.setup.xml") State setup) {
        JoinLinkJumpPointsJob job = new JoinLinkJumpPointsJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartContractFactory());
    }
}
