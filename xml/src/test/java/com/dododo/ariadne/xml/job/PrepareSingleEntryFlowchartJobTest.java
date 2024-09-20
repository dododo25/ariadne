package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareSingleEntryFlowchartJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareSingleEntryFlowchartJob.expected.xml") State expected,
                               @InputParam("PrepareSingleEntryFlowchartJob.setup.xml") State setup) {
        PrepareSingleEntryFlowchartJob job = new PrepareSingleEntryFlowchartJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new ExtendedFlowchartMouse());
    }
}
