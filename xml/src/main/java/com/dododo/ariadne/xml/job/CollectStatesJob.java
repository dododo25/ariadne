package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public final class CollectStatesJob extends AbstractJob {

    private final JaxbRootState rootState;

    private final int index;

    public CollectStatesJob(JaxbRootState rootState, int index) {
        super(String.format("%s_%d", CollectStatesJob.class.getSimpleName(), index));
        this.rootState = rootState;
        this.index = index;
    }

    @Override
    public void run() {
        File file = new File(getConfiguration().getInputFiles().get(index));

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //Setup schema validator
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(CollectStatesJob.class.getClassLoader().getResource("global.xsd"));
            factory.setSchema(schema);

            JAXBContext context = JAXBContext.newInstance(JaxbRootState.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ((JaxbRootState) unmarshaller.unmarshal(file))
                    .childrenStream()
                    .forEach(rootState::addChild);
        } catch (SAXException | JAXBException e) {
            throw new AriadneException(e);
        }
    }
}
