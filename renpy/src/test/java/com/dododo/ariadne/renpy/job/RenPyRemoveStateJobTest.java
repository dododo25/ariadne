package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.renpy.common.factory.RenPyFlowchartMouseFactory;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RenPyRemoveStateJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("RemoveStateJob.expected.xml") State expected,
                               @InputParam("RemoveStateJob.setup.xml") State setup) {
        RenPyRemoveStateJob<Statement> job = new RenPyRemoveStateJob<>(Statement.class);

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new RenPyFlowchartMouseFactory());
    }
}
