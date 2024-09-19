package com.dododo.ariadne.test.resolver;

import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

interface EdgeRule {

    void accept(Object o1, Object o2, Attributes attrs) throws InvocationTargetException, IllegalAccessException;

}