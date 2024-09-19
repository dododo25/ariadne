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
public final class Edge {

    @XmlAttribute
    private int from;

    @XmlAttribute
    private int to;

    @XmlAnyAttribute
    private Map<QName, String> rawAttrs;

    @XmlTransient
    private AttributesImpl attrs;

    public Edge() {
        this.rawAttrs = new HashMap<>();
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
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
        return String.format("%s(from=%d, to=%d, attrs=%s)", getClass().getSimpleName(), from, to, rawAttrs);
    }
}
