package com.dododo.ariadne.util.comparator;

import java.util.Comparator;

public class NullableStringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        }

        return o2 == null ? 1 : o1.compareTo(o2);
    }
}
