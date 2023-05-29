package com.dododo.ariadne.test.resolver;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.test.annotation.InputParam;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.model.Mapping;
import com.dododo.ariadne.test.rule.EdgeRule;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;
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
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

        loadRules();
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
        } catch (JAXBException e) {
            throw new ParameterResolutionException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ParameterResolutionException(String.format("%s at %s", e.getMessage(), inputAnnotation.value()));
        }
    }

    private void loadRules() throws IOException, URISyntaxException {
        Enumeration<URL> urls = FlowchartTypeResolver.class.getClassLoader().getResources(".");

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            Path path = Paths.get(url.toURI());

            loadRules(path, path);
        }
    }

    private void collectStates(Map<Integer, Object> map, Mapping mapping) {
        mapping.getNodes().forEach(node -> {
            Class<?> type = node.getType();

            if (type == null) {
                throw new IllegalArgumentException(String.format("Unknown type for %s", node));
            }

            NodeRule rule = nodeRules.get(type);

            if (rule == null) {
                throw new IllegalArgumentException(String.format("Unknown node rule for %s", type));
            }

            map.put(node.getId(), nodeRules.get(type).createState(node.getId(), node.getAttrs()));
        });
    }

    private void joinStates(Map<Integer, Object> map, Mapping mapping) {
        mapping.getEdges().forEach(edge -> {
            Object o1 = map.get(edge.getFrom());
            Object o2 = map.get(edge.getTo());

            EdgeRule rule = edgeRules.get(o1.getClass());

            if (rule == null) {
                throw new IllegalArgumentException(String.format("Unknown edge rule for %s", o1.getClass()));
            }

            rule.joinStates(o1, o2, edge.getAttrs());
        });
    }

    private void loadRules(Path root, Path current) {
        if (Files.isDirectory(current)) {
            try (Stream<Path> values = Files.list(current)) {
                values.forEach(next -> loadRules(root, next));
                return;
            } catch (IOException e) {
                throw new AriadneException(e);
            }
        }

        Class<?> type = prepareType(root.relativize(current));

        if (type != null) {
            loadRules(type);
        }
    }

    private Class<?> prepareType(Path path) {
        try {
            List<String> parts = new ArrayList<>();

            for (Path next : path) {
                parts.add(next.toString().replace(".class", ""));
            }

            return Class.forName(String.join(".", parts));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private void loadRules(Class<?> type) {
        try {
            Object obj = null;

            for (Method method : type.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(RuleSetSupplier.class)) {
                    continue;
                }

                if (obj == null) {
                    obj = type.newInstance();
                }

                RuleSet set = (RuleSet) method.invoke(obj);

                nodeRules.put(set.getType(), set.getNodeRule());
                edgeRules.put(set.getType(), set.getEdgeRule());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new AriadneException(e);
        }
    }
}
