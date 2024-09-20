package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxEdgeCell;
import com.dododo.ariadne.mxg.model.MxFile;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class SaveFlowchartJobTest {

    @TempDir
    private Path tempDir;

    @Test
    void testRunWhenSingleInputFileExistsShouldDoneWell() {
        testRunShouldDoneWell("file.txt.drawio", "file.txt");
    }

    @Test
    void testRunWhenMultipleInputFileExistShouldDoneWell() {
        testRunShouldDoneWell("combined.drawio", "file1.txt", "file2.txt");
    }

    private void testRunShouldDoneWell(String expected, String... files) {
        SaveFlowchartJob job = new SaveFlowchartJob(new AtomicReference<>(createMxFile()), null);

        job.setConfiguration(createConfig(files));
        job.run();

        Path newFilePath = tempDir.resolve(expected);

        if (!Files.exists(newFilePath)) {
            Assertions.fail();
        }
    }

    private Configuration createConfig(String... files) {
        Configuration config = mock(Configuration.class);

        when(config.getOutputDir())
                .thenReturn(tempDir.toString());
        when(config.getInputFiles())
                .thenReturn(Arrays.asList(files));

        return config;
    }

    private MxFile createMxFile() {
        MxFile file = mock(MxFile.class, RETURNS_DEEP_STUBS);
        DiagramRoot root = mock(DiagramRoot.class);

        root.getCells().add(mock(MxNodeCell.class));
        root.getCells().add(mock(MxEdgeCell.class));

        when(file.getDiagram().getModel().getRoot())
                .thenReturn(root);

        return file;
    }
}
