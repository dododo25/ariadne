package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.mxg.model.Diagram;
import com.dododo.ariadne.mxg.model.MxFile;
import com.dododo.ariadne.mxg.model.MxGraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

class PrepareMxFileJobTest {

    @Test
    void testRunShouldDoneWell() {
        AtomicReference<MxFile> mxFileRef = new AtomicReference<>();
        PrepareMxFileJob job = new PrepareMxFileJob(mxFileRef, new AtomicReference<>());

        job.run();

        Assertions.assertDoesNotThrow(() -> {
            Optional.of(mxFileRef.get())
                    .map(MxFile::getDiagram)
                    .map(Diagram::getModel)
                    .map(MxGraphModel::getRoot)
                    .orElseThrow(NullPointerException::new);
        });
    }
}
