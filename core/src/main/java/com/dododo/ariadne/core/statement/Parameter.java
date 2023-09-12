package com.dododo.ariadne.core.statement;

import java.util.Map;
import java.util.Objects;

public final class Parameter implements Statement {

    private final String name;

    private final Object value;

    private Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Object process(Map<String, ?> params) {
        return name == null ? value : params.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s(name=%s, value=%s)", getClass().getSimpleName(), name, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof Parameter && equals((Parameter) obj);
    }

    public static Parameter constant(Object value) {
        return new Parameter(null, value);
    }

    public static Parameter of(String name) {
        return new Parameter(name, null);
    }

    private boolean equals(Parameter parameter) {
        return Objects.equals(parameter.name, this.name)
                && Objects.equals(parameter.value, this.value);
    }
}
