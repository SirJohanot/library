package com.epam.library.service;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.shallowentityfiller.ShallowBookFiller;
import com.epam.library.service.shallowentityfiller.ShallowBookOrderFiller;
import com.epam.library.service.shallowentityfiller.ShallowEntityFiller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;
    private final ShallowEntityFiller<BookOrder> orderFiller = new ShallowBookOrderFiller(new ShallowBookFiller());

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public BookOrder buildPreviewOrder(int numberOfDays, RentalType type) {
        LocalDate currentDate = LocalDate.now();
        Date dummyStartDate = Date.valueOf(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dummyStartDate);
        calendar.add(Calendar.DATE, numberOfDays);
        Date dummyEndDate = new Date(calendar.getTimeInMillis());
        return new BookOrder(null, null, null, dummyStartDate, dummyEndDate, null, type, null);
    }

    @Override
    public void placeOrder(Date startDate, Date endDate, RentalType rentalType, Long bookId, Long userId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookDao bookDao = helper.createBookDao();
            bookDao.tweakAmount(bookId, -1);
            BookOrder newOrder = new BookOrder(null, Book.ofId(bookId), User.ofId(userId), startDate, endDate, null, rentalType, RentalState.ORDER_PLACED);
            BookOrderDao orderDao = helper.createBookOrderDao();
            orderDao.save(newOrder);
            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void advanceOrderState(Long orderId, RentalState newState) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookOrderDao orderDao = helper.createBookOrderDao();
            Optional<BookOrder> optionalBookOrder = orderDao.getById(orderId);
            if (optionalBookOrder.isEmpty()) {
                throw new ServiceException("Could not find the requested bookOrder");
            }
            BookOrder order = optionalBookOrder.get();
            Long bookId = order.getBook().getId();
            BookDao bookDao = helper.createBookDao();
            switch (newState) {
                case BOOK_COLLECTED:
                    bookDao.tweakAmount(bookId, -1);
                    break;
                case BOOK_RETURNED:
                    bookDao.tweakAmount(bookId, 1);
                    break;
            }
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
        return getOrdersOfUserId(null);
    }

    @Override
    public List<BookOrder> getUserOrders(Long userId) throws ServiceException {
        return getOrdersOfUserId(userId);
    }

    private List<BookOrder> getOrdersOfUserId(Long userId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            List<BookOrder> orderList = new ArrayList<>();
            BookOrderDao orderDao = helper.createBookOrderDao();
            for (BookOrder bookOrder : orderDao.getAll()) {
                if (bookOrder.getUser().getId().equals(userId) || userId == null) {
                    orderList.add(orderFiller.fillShallowEntity(bookOrder, helper));
                }
            }
            helper.endTransaction();
            return orderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
