package com.epam.library.service.comparator;

import java.util.Comparator;

public class NoSortingComparator<T> implements Comparator<T> {
    
    @Override
    public int compare(T o1, T o2) {
        return 0;
    }
}
