package com.epam.library.service;

import com.epam.library.entity.BookOrder;

public interface BookOrderService {

    BookOrder buildDummyOrder(String numberOfDaysLine, String rentalTypeLine);
}
