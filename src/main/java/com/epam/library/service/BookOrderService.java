package com.epam.library.service;

import com.epam.library.command.validation.Validator;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;

import java.sql.Date;
import java.util.List;

/**
 * This interface is concerned with using DAO objects to get BookOrders from the database and performing logical operations with them
 */

public interface BookOrderService {

    /**
     * Creates a preview order with startDate same as today and custom endDate and rentalType
     *
     * @param numberOfDays int value representing the gap (in days) between the desired endDate and startDate
     * @param type         the RentalType of desired BookOrder
     * @return BookOrder object with today's startDate, endDate numberOfDays ahead of startDate and RentalType same as type (other fields are null)
     */
    BookOrder buildPreviewOrder(int numberOfDays, RentalType type) throws ServiceException;

    /**
     * Uses DAO objects to save the BookOrder
     *
     * @param startDate  startDate of the BookOrder
     * @param endDate    endDate of the BookOrder
     * @param rentalType type of the BookOrder
     * @param bookId     id of the Book in the database related to the BookOrder
     * @param userId     id of the User in the database related to the BookOrder
     * @throws ServiceException if a DaoException occurs while saving the object to the database
     */
    void placeOrder(Date startDate, Date endDate, RentalType rentalType, Long bookId, Long userId, Validator<BookOrder> bookOrderValidator) throws ServiceException;

    /**
     * Sets new state for BookOrder in the database
     *
     * @param orderId  id of the BookOrder in the database
     * @param newState state to set
     * @throws ServiceException if a DaoException occurs
     */
    void advanceOrderState(Long orderId, RentalState newState) throws ServiceException;

    /**
     * Gets the BookOrder with the specified id
     *
     * @param id id of the desired order
     * @return BookOrder object from the database
     * @throws ServiceException if no BookOrder was found that had the specified id
     */
    BookOrder getOrderById(Long id) throws ServiceException;

    /**
     * Returns all BookOrders from the database, sorted from most priority for a Librarian for least priority
     *
     * @return List object containing BookOrders. May be empty
     * @throws ServiceException if a DaoException occurs
     */
    List<BookOrder> getOrdersForLibrarian() throws ServiceException;

    /**
     * Returns the User's BookOrders from the database, sorted from most priority for a Reader for least priority
     *
     * @param userId id of User
     * @return sorted List object containing BookOrders. May be empty
     * @throws ServiceException if a DaoException occurs
     */
    List<BookOrder> getReaderOrders(Long userId) throws ServiceException;
}
