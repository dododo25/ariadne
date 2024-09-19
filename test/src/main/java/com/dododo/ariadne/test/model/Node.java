package com.dododo.ariadne.test.model;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
public final class Node {

    @XmlAttribute(name = "type")
    private String rawType;

    @XmlAnyAttribute
    private Map<QName, String> rawAttrs;

    @XmlTransient
    private Integer id;

    @XmlTransient
    private Class<?> type;

    @XmlTransient
    private AttributesImpl attrs;

    public Node() {
        this.rawAttrs = new HashMap<>();
    }

    public Integer getId() {
        if (id == null) {
            id = Integer.valueOf(rawAttrs.getOrDefault(QName.valueOf("id"), null));
        }

        return id;
    }

    public Class<?> getType() {
        if (type == null) {
            try {
                type = Class.forName(rawType);
            } catch (ClassNotFoundException ignored) {
                // exception ignored
            }
        }

        return type;
    }

    public Attributes getAttrs() {
        if (attrs == null) {
            attrs = new AttributesImpl();

            rawAttrs.forEach((key, value) ->
                    attrs.addAttribute(null, null, key.toString(), null, value));
        }

        return attrs;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, type=%s, attributes=%s)",
                getClass().getSimpleName(), getId(), getType(), rawAttrs);
    }
}
