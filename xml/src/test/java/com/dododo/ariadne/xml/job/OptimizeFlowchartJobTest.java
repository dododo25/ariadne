package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
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

    private void test(State expected, State setup) {
        OptimizeFlowchartJob job = new OptimizeFlowchartJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartMouseFactory());
    }
}
