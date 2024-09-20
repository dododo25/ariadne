package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.assertions.BlockAssertions;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(FlowchartTypeResolver.class)
class ApplyLayoutJobTest {

    @Test
    void testRunWhenRootIsEntryBlockShouldDoneWell(@InputParam("ApplyLayoutJob.expected1.xml") Block expected,
                                                   @InputParam("ApplyLayoutJob.setup1.xml") Block setup) {
        testRunShouldDoneWell(expected, setup);
    }

    @Test
    void testRunWhenRootIsSwitchBlockShouldDoneWell(@InputParam("ApplyLayoutJob.expected2.xml") Block expected,
                                                    @InputParam("ApplyLayoutJob.setup2.xml") Block setup) {
        testRunShouldDoneWell(expected, setup);
    }

    @Test
    void testRunWhenBranchesHaveDifferentLengthShouldDoneWell(@InputParam("ApplyLayoutJob.expected3.xml") Block expected,
                                                              @InputParam("ApplyLayoutJob.setup3.xml") Block setup) {
        testRunShouldDoneWell(expected, setup);
    }

    @Test
    void testRunWhenRootIsMenuBlockShouldDoneWell(@InputParam("ApplyLayoutJob.expected4.xml") Block expected,
                                                  @InputParam("ApplyLayoutJob.setup4.xml") Block setup) {
        testRunShouldDoneWell(expected, setup);
    }

    @Test
    void testRunWhenLoopExistsShouldDoneWell(@InputParam("ApplyLayoutJob.expected5.xml") Block expected,
                                             @InputParam("ApplyLayoutJob.setup5.xml") Block setup) {
        testRunShouldDoneWell(expected, setup);
    }

    private void testRunShouldDoneWell(Block expected, Block setup) {
        ApplyLayoutJob job = new ApplyLayoutJob(new AtomicReference<>(), new AtomicReference<>());

        job.setRootBlock(setup);
        job.run();

        BlockAssertions.assertEquals(expected, job.getRootBlock(),
                BlockComparatorFactory::createWithPositionComparator);
    }
}
