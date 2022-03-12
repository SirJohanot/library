package com.epam.library.service.comparator;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OrderLibrarianPriorityComparator implements Comparator<BookOrder> {

    private static final Map<RentalState, Integer> librarianStatePriorityMap = new HashMap<>();

    static {
        librarianStatePriorityMap.put(RentalState.BOOK_RETURNED, 1);
        librarianStatePriorityMap.put(RentalState.ORDER_DECLINED, 2);
        librarianStatePriorityMap.put(RentalState.BOOK_COLLECTED, 3);
        librarianStatePriorityMap.put(RentalState.ORDER_APPROVED, 4);
        librarianStatePriorityMap.put(RentalState.ORDER_PLACED, 5);
    }

    @Override
    public int compare(BookOrder o1, BookOrder o2) {
        RentalState firstOrderState = o1.getState();
        Integer firstPriority = librarianStatePriorityMap.get(firstOrderState);

        RentalState secondOrderState = o2.getState();
        Integer secondPriority = librarianStatePriorityMap.get(secondOrderState);
        
        return secondPriority - firstPriority;
    }
}
