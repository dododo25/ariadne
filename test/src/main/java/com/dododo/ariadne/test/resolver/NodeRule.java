package com.dododo.ariadne.test.resolver;

import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

interface NodeRule {

    Object apply(Integer id, Attributes attrs) throws InvocationTargetException, IllegalAccessException;

}