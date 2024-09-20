package com.dododo.ariadne.test.resolver;

import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.model.Edge;
import com.dododo.ariadne.test.model.Mapping;
import com.dododo.ariadne.test.model.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class FlowchartTypeResolver implements ParameterResolver {

    private final Unmarshaller unmarshaller;

    private final Map<Class<?>, NodeRule> nodeRules;

    private final Map<Class<?>, EdgeRule> edgeRules;

    public FlowchartTypeResolver() throws SAXException, IOException, URISyntaxException, JAXBException {
        //Setup schema validator
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(FlowchartTypeResolver.class.getResource("/test.xsd"));

        JAXBContext context = JAXBContext.newInstance(Mapping.class);

        this.unmarshaller = context.createUnmarshaller();
        this.unmarshaller.setSchema(schema);

        this.nodeRules = new HashMap<>();
        this.edgeRules = new HashMap<>();

        RulesetLoader.load(nodeRules, edgeRules);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.isAnnotated(InputParam.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        InputParam inputAnnotation = parameterContext.findAnnotation(InputParam.class)
                .orElseThrow(IllegalArgumentException::new);

        try {
            URL url = extensionContext.getTestClass()
                    .map(testType -> testType.getResource(inputAnnotation.value()))
                    .orElseThrow(IllegalArgumentException::new);

            Mapping mapping = (Mapping) unmarshaller.unmarshal(url);
            Map<Integer, Object> map = new HashMap<>();

            collectStates(map, mapping);
            joinStates(map, mapping);

            return map.get(mapping.getRoot());
        } catch (JAXBException | InvocationTargetException | IllegalAccessException e) {
            throw new ParameterResolutionException(
                    String.format("%s at %s", e.getMessage(), inputAnnotation.value()), e);
        } catch (IllegalArgumentException e) {
            throw new ParameterResolutionException(
                    String.format("%s at %s", e.getMessage(), inputAnnotation.value()));
        }
    }

    private void collectStates(Map<Integer, Object> map, Mapping mapping)
            throws InvocationTargetException, IllegalAccessException {
        for (Node node : mapping.getNodes()) {
            Class<?> type = node.getType();

            if (type == null) {
                throw new IllegalArgumentException(String.format("Unknown type for %s", node));
            }

            NodeRule rule = nodeRules.get(type);

            if (rule == null) {
                throw new IllegalArgumentException(String.format("Unknown node rule for %s", type));
            }

            map.put(node.getId(), nodeRules.get(type).apply(node.getId(), node.getAttrs()));
        }
    }

    private void joinStates(Map<Integer, Object> map, Mapping mapping)
            throws InvocationTargetException, IllegalAccessException {
        for (Edge edge : mapping.getEdges()) {
            Object o1 = map.get(edge.getFrom());
            Object o2 = map.get(edge.getTo());

            EdgeRule rule = edgeRules.get(o1.getClass());

            if (rule == null) {
                throw new IllegalArgumentException(String.format("Unknown edge rule for %s", o1.getClass()));
            }

            rule.accept(o1, o2, edge.getAttrs());
        }
    }
}
