package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;

import java.sql.Date;
import java.util.List;

public interface BookOrderDao extends Dao<BookOrder> {

    void setState(Long id, RentalState newState) throws DaoException;

    void setReturnDate(Long id, Date returnDate) throws DaoException;

    List<BookOrder> getOrdersOfUser(Long userId) throws DaoException;
}
