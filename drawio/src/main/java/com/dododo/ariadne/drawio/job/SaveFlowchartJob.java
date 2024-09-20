package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.core.exception.AriadneException;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.mxg.model.MxFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public final class SaveFlowchartJob extends DrawIoAbstractJob {

    public static final String FILE_TEMPLATE = "%s/%s.drawio";

    public SaveFlowchartJob(AtomicReference<MxFile> mxFileRef, AtomicReference<Block> rootBlockRef) {
        super(mxFileRef, rootBlockRef);
    }

    @Override
    public void run() {
        try {
            JAXBContext context = JAXBContext.newInstance(MxFile.class);
            Marshaller mar = context.createMarshaller();

            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mar.marshal(getMxFile(), createOutputFile());
        } catch (JAXBException e) {
            throw new AriadneException(e);
        }
    }

    private File createOutputFile() {
        Configuration configuration = getConfiguration();

        String directory = configuration.getOutputDir();
        String filename = configuration.getInputFiles().size() > 1
                ? "combined"
                : new File(configuration.getInputFiles().get(0)).getName();

        return new File(String.format(FILE_TEMPLATE, directory, filename));
    }
}
