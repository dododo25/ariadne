package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class JoinStatesJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("JoinStatesJob.expected.xml") State expected,
                               @InputParam("JoinStatesJob.setup.xml") JaxbState setup) {
        JoinStatesJob job = new JoinStatesJob(setup);
        job.run();
        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartMouseFactory());
    }
}
