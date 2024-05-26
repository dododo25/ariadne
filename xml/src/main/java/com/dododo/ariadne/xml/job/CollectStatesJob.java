package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.mouse.ParentFirstJaxbFlowchartMouse;
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

            JaxbFlowchartContract contract = new JaxbFlowchartContractAdapter() {
                @Override
                public void accept(JaxbRootState state) {
                    acceptComplexState(state);
                }

                @Override
                public void accept(JaxbMenu menu) {
                    acceptComplexState(menu);
                }

                @Override
                public void accept(JaxbOption option) {
                    acceptComplexState(option);
                }

                @Override
                public void accept(JaxbComplexSwitch complexSwitch) {
                    acceptComplexState(complexSwitch);
                }

                @Override
                public void accept(JaxbSwitchBranch switchBranch) {
                    acceptComplexState(switchBranch);
                }

                private void acceptComplexState(JaxbComplexState state) {
                    state.childrenStream()
                            .forEach(child -> child.setRoot(state));
                }
            };

            ((JaxbRootState) unmarshaller.unmarshal(file))
                    .childrenStream()
                    .forEach(rootState::addChild);

            new ParentFirstJaxbFlowchartMouse().accept(rootState, contract);
        } catch (SAXException | JAXBException e) {
            throw new AriadneException(e);
        }
    }
}
