package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.common.configuration.Configuration;
import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.thread.model.Block;
import com.dododo.ariadne.mxg.MxFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class SaveFlowchartJob extends ThreadAbstractJob {

    public static final String TAR_FILE_TEMPLATE = "%s/%s.ard";

    public SaveFlowchartJob(AtomicReference<MxFile> mxFileRef,
                            AtomicReference<JaxbState> jaxbStateRef,
                            AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, jaxbStateRef, rootBlockRef);
    }

    @Override
    public void run() {
        Path outputPath = createOutputPath();

        try (OutputStream fOut = Files.newOutputStream(outputPath);
             BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
             TarArchiveOutputStream tOut = new TarArchiveOutputStream(buffOut)) {
            prepareMxFile();
            prepareFlowchartFile();

            for (Path path : Arrays.asList(Paths.get("mx"), Paths.get("flowchart"))) {
                TarArchiveEntry tarEntry = new TarArchiveEntry(path.toFile(), path.getFileName().toString());

                tOut.putArchiveEntry(tarEntry);

                // copy file to TarArchiveOutputStream
                Files.copy(path, tOut);
                Files.delete(path);

                tOut.closeArchiveEntry();
            }
        } catch (IOException e) {
            throw new AriadneException(e);
        }
    }

    private void prepareMxFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(MxFile.class);
            Marshaller mar = context.createMarshaller();

            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mar.marshal(getMxFile(), new File("mx"));
        } catch (JAXBException e) {
            throw new AriadneException(e);
        }
    }

    private void prepareFlowchartFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(JaxbRootState.class);
            Marshaller mar = context.createMarshaller();

            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mar.marshal(getJaxbState(), new File("flowchart"));
        } catch (JAXBException e) {
            throw new AriadneException(e);
        }
    }

    private Path createOutputPath() {
        Configuration configuration = getConfiguration();

        String directory = configuration.getOutputDir();
        String filename = configuration.getInputFiles().size() > 1
                ? "combined"
                : new File(configuration.getInputFiles().get(0)).getName();

        return Paths.get(String.format(TAR_FILE_TEMPLATE, directory, filename));
    }
}
