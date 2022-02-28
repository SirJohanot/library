package com.epam.library.service;

import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalType;

import java.time.LocalDate;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public BookOrder buildDummyOrder(String numberOfDaysLine, String rentalTypeLine) {
        int numberOfDays = Integer.parseInt(numberOfDaysLine);
        RentalType rentalType = RentalType.valueOf(rentalTypeLine);
        LocalDate currentDate = LocalDate.now();
        return null; //TODO: add implementation
    }
}
