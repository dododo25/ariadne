package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RearrangeVariableGroupComplexStateStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RearrangeVariableGroupStatesJob.expected.xml") State expected,
                               @InputParam("RearrangeVariableGroupStatesJob.setup.xml") State setup) {
        RearrangeVariableGroupStatesJob job = new RearrangeVariableGroupStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, setup, new RenPyFlowchartMouse());
    }
}
