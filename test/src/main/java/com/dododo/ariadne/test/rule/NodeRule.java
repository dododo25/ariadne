package com.dododo.ariadne.test.rule;

import org.xml.sax.Attributes;

public interface NodeRule {

    Object createState(int id, Attributes attrs);
}
