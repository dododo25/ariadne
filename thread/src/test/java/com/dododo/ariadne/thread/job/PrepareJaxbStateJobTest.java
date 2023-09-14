package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jt.assertions.JaxbAssertions;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareJaxbStateJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareJaxbStateJob.expected.xml") JaxbState expected,
                               @InputParam("PrepareJaxbStateJob.setup.xml") State setup) {
        PrepareJaxbStateJob job = new PrepareJaxbStateJob(new AtomicReference<>());

        job.setFlowchart(setup);
        job.run();

        JaxbAssertions.assertEquals(expected, job.getJaxbState());
    }
}
