package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.drawio.assertions.BlockAssertions;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(FlowchartTypeResolver.class)
class PrepareRootBlockJobTest {

    @Test
    void testRunShouldDoneWell(@InputParam("PrepareRootBlockJob.expected.xml") Block expected,
                               @InputParam("PrepareRootBlockJob.setup.xml") State setup) {
        AtomicReference<Block> rootBlockRef = new AtomicReference<>();
        PrepareRootBlockJob job = new PrepareRootBlockJob(new AtomicReference<>(), rootBlockRef);

        job.setFlowchart(setup);
        job.run();

        BlockAssertions.assertEquals(expected, job.getRootBlock(), BlockComparatorFactory::createSimpleComparator);
    }
}
