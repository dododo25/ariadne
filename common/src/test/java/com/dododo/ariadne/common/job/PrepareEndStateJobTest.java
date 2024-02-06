package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.factory.FlowchartContractFactory;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareEndStateJobTest {

    @Test
    void testRunWhenNullOnChainStatesShouldDoneWell(@InputParam("PrepareEndStateJob.expected1.xml") State expected,
                                                    @InputParam("PrepareEndStateJob.setup1.xml") State setup) {
        test(expected, setup);
    }

    @Test
    void testRunWhenNullOnSwitchBranchStatesShouldDoneWell(@InputParam("PrepareEndStateJob.expected2.xml") State expected,
                                                           @InputParam("PrepareEndStateJob.setup2.xml") State setup) {
        test(expected, setup);
    }

    private void test(State expected, State setup) {
        PrepareEndStateJob job = new PrepareEndStateJob();

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new FlowchartContractFactory());
    }
}
