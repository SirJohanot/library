package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;

import java.sql.Date;
import java.util.List;

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

    /**
     * Gets all orders associated with a User
     *
     * @param userId id of User in the database
     * @return List object containing all BookOrders of the User. May be empty if the User does not have any orders related to it or if there is no User of userId in the database
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    List<BookOrder> getOrdersOfUser(Long userId) throws DaoException;
}
