package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.exception.AriadneException;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.xml.handler.XmlFlowchartHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public final class CollectStatesJob extends AbstractJob {

    private final int index;

    public CollectStatesJob(int index) {
        super(String.format("%s_%d", CollectStatesJob.class.getSimpleName(), index));
        this.index = index;
    }

    @Override
    public void run() {
        File file = new File(getConfiguration().getInputFiles().get(index));

        try {
            //Setup schema validator
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(CollectStatesJob.class.getClassLoader().getResource("global.xsd"));

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setSchema(schema);

            SAXParser parser = factory.newSAXParser();
            parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            parser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            parser.parse(file, new XmlFlowchartHandler((ComplexState) getFlowchart()));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new AriadneException(e);
        }
    }
}
