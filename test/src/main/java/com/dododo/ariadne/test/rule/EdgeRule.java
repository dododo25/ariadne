package com.dododo.ariadne.test.rule;

import org.xml.sax.Attributes;

public interface EdgeRule {

    void joinStates(Object o1, Object o2, Attributes attrs);
}
