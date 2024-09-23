package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinRootStateChildrenJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinRootStateChildrenJobTest.expected.xml") State expected,
                               @InputParam("JoinRootStateChildrenJobTest.setup.xml") State setup) {
        JoinRootStateChildrenJob job = new JoinRootStateChildrenJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouse());
    }
}