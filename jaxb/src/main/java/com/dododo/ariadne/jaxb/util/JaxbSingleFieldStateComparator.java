package com.dododo.ariadne.jaxb.util;

import com.dododo.ariadne.jaxb.model.JaxbSimpleState;
import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.Comparator;

public class JaxbSingleFieldStateComparator implements Comparator<JaxbState> {

    private final NullableStringComparator comparator;

    public JaxbSingleFieldStateComparator() {
        this.comparator = new NullableStringComparator();
    }

    @Override
    public int compare(JaxbState o1, JaxbState o2) {
        if (o1.getClass() != o2.getClass()) {
            return 1;
        }

        String v1 = ((JaxbSimpleState) o1).getValue();
        String v2 = ((JaxbSimpleState) o2).getValue();

        return comparator.compare(v1, v2);
    }
}
