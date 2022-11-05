package com.patiun.library.comparator;

import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OrderLibrarianPriorityComparator implements Comparator<BookOrder> {

    private static final Map<RentalState, Integer> LIBRARIAN_STATE_PRIORITY_MAP = new HashMap<>();

    static {
        LIBRARIAN_STATE_PRIORITY_MAP.put(RentalState.BOOK_RETURNED, 1);
        LIBRARIAN_STATE_PRIORITY_MAP.put(RentalState.ORDER_DECLINED, 2);
        LIBRARIAN_STATE_PRIORITY_MAP.put(RentalState.BOOK_COLLECTED, 3);
        LIBRARIAN_STATE_PRIORITY_MAP.put(RentalState.ORDER_APPROVED, 4);
        LIBRARIAN_STATE_PRIORITY_MAP.put(RentalState.ORDER_PLACED, 5);
    }

    @Override
    public int compare(BookOrder o1, BookOrder o2) {
        RentalState firstOrderState = o1.getState();
        Integer firstPriority = LIBRARIAN_STATE_PRIORITY_MAP.get(firstOrderState);

        RentalState secondOrderState = o2.getState();
        Integer secondPriority = LIBRARIAN_STATE_PRIORITY_MAP.get(secondOrderState);

        return secondPriority - firstPriority;
    }
}
