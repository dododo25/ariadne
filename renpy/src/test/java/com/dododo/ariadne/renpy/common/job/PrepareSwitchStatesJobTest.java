package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.renpy.common.mouse.ParentFirstRenPyFlowchartMouse;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareSwitchStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareSwitchStatesJob.expected.xml") State expected,
                               @InputParam("PrepareSwitchStatesJob.setup.xml") State setup) {
        PrepareSwitchStatesJob job = new PrepareSwitchStatesJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ParentFirstRenPyFlowchartMouse());
    }
}
