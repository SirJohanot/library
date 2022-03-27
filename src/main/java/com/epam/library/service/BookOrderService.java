package com.epam.library.service;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.specification.Specification;

import java.util.Comparator;
import java.util.List;

/**
 * This interface is concerned with using DAO objects to get BookOrders from the database and performing logical operations with them
 */

public interface BookOrderService {

    /**
     * Uses DAO objects to save the BookOrder
     *
     * @param numberOfDays integer representing the length of the order in days
     * @param rentalType   type of the BookOrder
     * @param bookId       id of the Book in the database related to the BookOrder
     * @param userId       id of the User in the database related to the BookOrder
     * @throws ServiceException    if a DaoException occurs while saving the object to the database
     * @throws ValidationException if the BookOrder with the passed parameters is deemed invalid
     */
    void placeOrder(int numberOfDays, RentalType rentalType, Long bookId, Long userId) throws ServiceException, ValidationException;

    /**
     * Sets new state for BookOrder in the database
     *
     * @param orderId  id of the BookOrder in the database
     * @param userId   id of User that is changing the state
     * @param newState state to set
     * @throws ServiceException if a DaoException occurs
     */
    void advanceOrderState(Long orderId, Long userId, RentalState newState) throws ServiceException;

    /**
     * Gets the BookOrder with the specified id
     *
     * @param id id of the desired order
     * @return BookOrder object from the database
     * @throws ServiceException if no BookOrder was found that had the specified id
     */
    BookOrder getOrderById(Long id) throws ServiceException;

    /**
     * Returns BookOrders from the database, which fit the passed specification, in a List sorted with the passed Comparator
     *
     * @param bookOrderSpecification specification that the orders have to fit
     * @param comparator             comparator that the specified orders will be sorted by
     * @return a sorted List containing the specified orders
     * @throws ServiceException if a DaoException occurs
     */
    List<BookOrder> getSpecifiedOrders(Specification<BookOrder> bookOrderSpecification, Comparator<BookOrder> comparator) throws ServiceException;

}
