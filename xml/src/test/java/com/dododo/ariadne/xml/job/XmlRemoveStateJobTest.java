package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class XmlRemoveStateJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("XmlRemoveStateJob.expected.xml") State expected,
                               @InputParam("XmlRemoveStateJob.setup.xml") State setup) {
        XmlRemoveStateJob<Statement> job = new XmlRemoveStateJob<>(Statement.class);

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartMouseFactory());
    }
}
