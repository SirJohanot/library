package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;

import java.sql.Date;

/**
 * This interface is concerned with manipulating BookOrders' state inside the database
 */

public interface BookOrderDao extends Dao<BookOrder> {

    /**
     * Changes the BookOrder's rental_state column value in the database
     *
     * @param id       id of the BookOrder in the database
     * @param newState the RentalState to change to
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void setState(Long id, RentalState newState) throws DaoException;

    /**
     * Changes the BookOrder's return_date column value in the database
     *
     * @param id         id of the BookOrder in the database
     * @param returnDate the Date to change to
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void setReturnDate(Long id, Date returnDate) throws DaoException;

}
