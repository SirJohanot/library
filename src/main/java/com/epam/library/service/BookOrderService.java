package com.epam.library.service;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface BookOrderService {

    BookOrder buildPreviewOrder(int numberOfDays, RentalType type);

    void placeOrder(Date startDate, Date endDate, RentalType rentalType, Long bookId, Long userId) throws ServiceException;

    void advanceOrderState(Long orderId, RentalState newState) throws ServiceException;

    BookOrder getOrderById(Long id) throws ServiceException;

    List<BookOrder> getOrdersForLibrarian() throws ServiceException;

    List<BookOrder> getReaderOrders(Long userId) throws ServiceException;
}
