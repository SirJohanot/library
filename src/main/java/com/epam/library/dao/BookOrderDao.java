package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;

import java.sql.Date;

public interface BookOrderDao extends Dao<BookOrder> {

    void setState(Long id, RentalState newState) throws DaoException;

    void setReturnDate(Long id, Date returnDate) throws DaoException;
}
