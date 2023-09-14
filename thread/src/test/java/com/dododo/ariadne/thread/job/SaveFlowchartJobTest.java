package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.mxg.DiagramRoot;
import com.dododo.ariadne.mxg.MxEdgeCell;
import com.dododo.ariadne.mxg.MxFile;
import com.dododo.ariadne.mxg.MxNodeCell;
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
        testRunShouldDoneWell("file.txt.ard", "file.txt");
    }

    @Test
    void testRunWhenMultipleInputFileExistShouldDoneWell() {
        testRunShouldDoneWell("combined.ard", "file1.txt", "file2.txt");
    }

    private void testRunShouldDoneWell(String expected, String... files) {
        SaveFlowchartJob job = new SaveFlowchartJob(new AtomicReference<>(createMxFile()),
                new AtomicReference<>(createJaxbState()), null);

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

    private JaxbState createJaxbState() {
        JaxbRootState jaxbState = mock(JaxbRootState.class, RETURNS_DEEP_STUBS);
        DiagramRoot root = mock(DiagramRoot.class);

        root.getCells().add(mock(MxNodeCell.class));
        root.getCells().add(mock(MxEdgeCell.class));

        when(jaxbState.childrenCount())
                .thenReturn(2);

        when(jaxbState.childAt(0))
                .thenReturn(mock(JaxbText.class));
        when(jaxbState.childAt(1))
                .thenReturn(mock(JaxbText.class));

        return jaxbState;
    }
}
