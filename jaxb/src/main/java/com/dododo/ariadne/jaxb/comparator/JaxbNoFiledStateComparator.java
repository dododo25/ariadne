package com.dododo.ariadne.jaxb.comparator;

import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.Comparator;

public class JaxbNoFiledStateComparator implements Comparator<JaxbState> {

    @Override
    public int compare(JaxbState o1, JaxbState o2) {
        return o1.getClass().toString().compareTo(o2.getClass().toString());
    }
}
