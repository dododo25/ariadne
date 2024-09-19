package com.dododo.ariadne.test.resolver;

import com.dododo.ariadne.test.annotation.Ruleset;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class RulesetLoader {

    private final Path root;

    private final Map<Class<?>, NodeRule> nodeRules;

    private final Map<Class<?>, EdgeRule> edgeRules;

    public RulesetLoader(URL url,
                         Map<Class<?>, NodeRule> nodeRules,
                         Map<Class<?>, EdgeRule> edgeRules) throws URISyntaxException {
        this.root = Paths.get(url.toURI());
        this.nodeRules = nodeRules;
        this.edgeRules = edgeRules;
    }

    public static void load(
            Map<Class<?>, NodeRule> nodeRules,
            Map<Class<?>, EdgeRule> edgeRules) throws IOException, URISyntaxException {
        Enumeration<URL> urls = RulesetLoader.class.getClassLoader().getResources(".");

        while (urls.hasMoreElements()) {
            new RulesetLoader(urls.nextElement(), nodeRules, edgeRules).loadRules();
        }
    }

    private void loadRules() {
        loadRules(root);
    }

    private void loadRules(Path current) {
        if (Files.isDirectory(current)) {
            try (Stream<Path> values = Files.list(current)) {
                values.forEach(this::loadRules);
                return;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        Class<?> type = prepareType(root.relativize(current));

        if (type != null && type.isAnnotationPresent(Ruleset.class)) {
            loadRules(type);
        }
    }

    private Class<?> prepareType(Path path) {
        try {
            StringBuilder builder = new StringBuilder();

            for (int i = 0 ; i < path.getNameCount(); i++) {
                String part = path.getName(i).toString();

                if (i == path.getNameCount() - 1) {
                    part = part.replace(".class", "");
                }

                builder.append(part);

                if (i < path.getNameCount() - 1) {
                    builder.append(".");
                }
            }

            return Class.forName(builder.toString());
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return null;
        }
    }

    private void loadRules(Class<?> type) {
        try {
            Object obj = type.getDeclaredConstructor().newInstance();

            for (Method method : type.getDeclaredMethods()) {
                if (method.isAnnotationPresent(com.dododo.ariadne.test.annotation.NodeRule.class)) {
                    prepareNodeRule(obj, method);
                }

                if (method.isAnnotationPresent(com.dododo.ariadne.test.annotation.EdgeRule.class)) {
                    prepareEdgeRule(obj, method);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void prepareNodeRule(Object obj, Method method) {
        com.dododo.ariadne.test.annotation.NodeRule rule =
                method.getAnnotation(com.dododo.ariadne.test.annotation.NodeRule.class);

        List<Container> pairs = new ArrayList<>();

        for (int i = 0; i < rule.params().length; i++) {
            pairs.add(new Container(method.getParameters()[i].getType(), rule.params()[i]));
        }

        nodeRules.put(rule.type(), (id, attrs) -> method.invoke(obj, pairs.stream()
                .map(c -> prepareAttributeValue(attrs.getValue(c.attrName), c.type))
                .toArray()));
    }

    private void prepareEdgeRule(Object obj, Method method) {
        com.dododo.ariadne.test.annotation.EdgeRule rule =
                method.getAnnotation(com.dododo.ariadne.test.annotation.EdgeRule.class);

        List<Container> pairs = new ArrayList<>();

        for (int i = 0; i < rule.params().length; i++) {
            pairs.add(new Container(method.getParameters()[i].getType(), rule.params()[i]));
        }

        Stream.of(rule.type()).forEach(type -> edgeRules.put(type, (o1, o2, attrs) -> {
            Object[] arguments = Stream.concat(
                            Stream.of(o1, o2),
                            pairs.stream().map(c -> prepareAttributeValue(attrs.getValue(c.attrName), c.type)))
                    .toArray();

            method.invoke(obj, arguments);
        }));
    }

    @SuppressWarnings("java:S3776")
    private Object prepareAttributeValue(String valueAsString, Class<?> type) {
        if (valueAsString == null) {
            return prepareDefaultAttributeValue(type);
        }

        if (boolean.class.isAssignableFrom(type)) {
            return Boolean.parseBoolean(valueAsString);
        } else if (byte.class.isAssignableFrom(type)) {
            return Byte.parseByte(valueAsString);
        } else if (char.class.isAssignableFrom(type)) {
            return valueAsString.charAt(0);
        } else if (short.class.isAssignableFrom(type)) {
            return Short.parseShort(valueAsString);
        } else if (int.class.isAssignableFrom(type)) {
            return Integer.parseInt(valueAsString);
        } else if (long.class.isAssignableFrom(type)) {
            return Long.parseLong(valueAsString);
        } else if (float.class.isAssignableFrom(type)) {
            return Float.parseFloat(valueAsString);
        } else if (double.class.isAssignableFrom(type)) {
            return Double.parseDouble(valueAsString);
        } else if (Boolean.class.isAssignableFrom(type)) {
            return Boolean.valueOf(valueAsString);
        } else if (Byte.class.isAssignableFrom(type)) {
            return Byte.valueOf(valueAsString);
        } else if (Character.class.isAssignableFrom(type)) {
            return valueAsString.isEmpty() ? null : valueAsString.charAt(0);
        } else if (Short.class.isAssignableFrom(type)) {
            return Short.valueOf(valueAsString);
        } else if (Integer.class.isAssignableFrom(type)) {
            return Integer.valueOf(valueAsString);
        } else if (Long.class.isAssignableFrom(type)) {
            return Long.valueOf(valueAsString);
        } else if (Float.class.isAssignableFrom(type)) {
            return Float.valueOf(valueAsString);
        } else if (Double.class.isAssignableFrom(type)) {
            return Double.valueOf(valueAsString);
        }

        return valueAsString;
    }

    private Object prepareDefaultAttributeValue(Class<?> type) {
        if (boolean.class.isAssignableFrom(type)) {
            return false;
        } else if (byte.class.isAssignableFrom(type)) {
            return (byte) 0;
        } else if (char.class.isAssignableFrom(type)) {
            return (char) 0;
        } else if (short.class.isAssignableFrom(type)) {
            return (short) 0;
        } else if (int.class.isAssignableFrom(type)) {
            return 0;
        } else if (long.class.isAssignableFrom(type)) {
            return (long) 0;
        } else if (float.class.isAssignableFrom(type)) {
            return (float) 0;
        } else if (double.class.isAssignableFrom(type)) {
            return (double) 0;
        }

        return null;
    }

    private static class Container {

        private final Class<?> type;

        private final String attrName;

        private Container(Class<?> type, String attrName) {
            this.type = type;
            this.attrName = attrName;
        }
    }
}
