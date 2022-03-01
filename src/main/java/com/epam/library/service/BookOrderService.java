package com.epam.library.service;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;

import java.util.List;

public interface BookOrderService {

    BookOrder buildPreviewOrder(int numberOfDays, RentalType type);

    void setOrderState(Long orderId, RentalState newState) throws ServiceException;

    BookOrder getOrderById(Long id) throws ServiceException;

    List<BookOrder> getAllOrders() throws ServiceException;

    List<BookOrder> getUserOrders(Long userId) throws ServiceException;
}
