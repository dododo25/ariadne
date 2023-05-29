package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinLabelledGroupCallStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinLabelledGroupCallStatesJob.expected.xml") State expected,
                               @InputParam("JoinLabelledGroupCallStatesJob.setup.xml") State setup) {
        PrepareGroupCallStatesJob job = new PrepareGroupCallStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouseFactory());
    }
}
