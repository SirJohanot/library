package com.epam.library.service;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public BookOrder buildPreviewOrder(int numberOfDays, RentalType type) {
        LocalDate currentDate = LocalDate.now();
        return null; //TODO: add implementation
    }

    @Override
    public void setOrderState(Long orderId, RentalState newState) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookOrderDao orderDao = helper.createBookOrderDao();
            orderDao.setState(orderId, newState);
            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BookOrder getOrderById(Long id) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookOrderDao orderDao = helper.createBookOrderDao();
            Optional<BookOrder> order = orderDao.getById(id);
            if (order.isEmpty()) {
                throw new ServiceException("The requested order does not exist");
            }
            helper.endTransaction();
            return order.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookOrder> getAllOrders() throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            List<BookOrder> orderList = new ArrayList<>();
            BookOrderDao orderDao = helper.createBookOrderDao();
            for (BookOrder book : orderDao.getAll()) {
//                orderList.add(shallowBookToActualBook(book, helper)); TODO: implementaion
            }
            helper.endTransaction();
            return orderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookOrder> getUserOrders(Long userId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            List<BookOrder> orderList = new ArrayList<>();
            BookOrderDao orderDao = helper.createBookOrderDao();
            for (BookOrder book : orderDao.getAll()) {
//                orderList.add(shallowBookToActualBook(book, helper)); TODO: implementaion
            }
            helper.endTransaction();
            return orderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
