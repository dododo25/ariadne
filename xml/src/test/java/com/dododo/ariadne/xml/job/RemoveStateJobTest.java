package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.ct.assertions.StateAssertions;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlowchartTypeResolver.class)
class RemoveStateJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("XmlRemoveStateJob.expected.xml") State expected,
                               @InputParam("XmlRemoveStateJob.setup.xml") State setup) {
        RemoveStateJob<Text> job = new RemoveStateJob<>(Text.class);

        job.setFlowchart(setup);
        job.run();

        StateAssertions.assertEquals(expected, job.getFlowchart(), new XmlFlowchartMouse());
    }
}
