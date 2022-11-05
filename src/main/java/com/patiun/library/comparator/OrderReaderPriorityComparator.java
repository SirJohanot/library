package com.patiun.library.comparator;

import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OrderReaderPriorityComparator implements Comparator<BookOrder> {

    private static final Map<RentalState, Integer> READER_STATE_PRIORITY_MAP = new HashMap<>();

    static {
        READER_STATE_PRIORITY_MAP.put(RentalState.BOOK_RETURNED, 1);
        READER_STATE_PRIORITY_MAP.put(RentalState.ORDER_DECLINED, 2);
        READER_STATE_PRIORITY_MAP.put(RentalState.ORDER_PLACED, 3);
        READER_STATE_PRIORITY_MAP.put(RentalState.BOOK_COLLECTED, 4);
        READER_STATE_PRIORITY_MAP.put(RentalState.ORDER_APPROVED, 5);
    }

    @Override
    public int compare(BookOrder o1, BookOrder o2) {
        RentalState firstOrderState = o1.getState();
        Integer firstPriority = READER_STATE_PRIORITY_MAP.get(firstOrderState);

        RentalState secondOrderState = o2.getState();
        Integer secondPriority = READER_STATE_PRIORITY_MAP.get(secondOrderState);

        return secondPriority - firstPriority;
    }
}
