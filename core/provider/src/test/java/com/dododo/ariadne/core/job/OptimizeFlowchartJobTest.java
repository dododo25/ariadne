package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.test.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class OptimizeFlowchartJobTest {

    @Test
    void testRunWhenSelfReferencingSwitchesExistsShouldDoneWell(@InputParam("OptimizeFlowchartJob.expected1.xml") State expected,
                                                                @InputParam("OptimizeFlowchartJob.setup1.xml") State setup) {
        test(expected, setup);
    }

    @Test
    void testRunWhenRedundantSwitchesExistsShouldDoneWell(@InputParam("OptimizeFlowchartJob.expected2.xml") State expected,
                                                          @InputParam("OptimizeFlowchartJob.setup2.xml") State setup) {
        test(expected, setup);
    }

    @Test
    void testRunWhenRedundantMenusExistsShouldDoneWell(@InputParam("OptimizeFlowchartJob.expected3.xml") State expected,
                                                       @InputParam("OptimizeFlowchartJob.setup3.xml") State setup) {
        test(expected, setup);
    }

    private void test(State expected, State setup) {
        OptimizeFlowchartJob job = new OptimizeFlowchartJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new FlowchartMouse());
    }
}
