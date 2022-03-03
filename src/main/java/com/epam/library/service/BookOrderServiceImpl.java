package com.epam.library.service;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.comparator.OrderLibrarianPriorityComparator;
import com.epam.library.service.comparator.OrderReaderPriorityComparator;
import com.epam.library.service.shallowentityfiller.ShallowEntityFiller;
import com.epam.library.service.shallowentityfiller.ShallowEntityFillerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;
    private final ShallowEntityFillerFactory fillerFactory;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory, ShallowEntityFillerFactory fillerFactory) {
        this.daoHelperFactory = daoHelperFactory;
        this.fillerFactory = fillerFactory;
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
                case ORDER_APPROVED:
                    bookDao.tweakAmount(bookId, -1);
                    break;
                case BOOK_RETURNED:
                    Date currentDate = Date.valueOf(LocalDate.now());
                    orderDao.setReturnDate(orderId, currentDate);
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
            Optional<BookOrder> optionalOrder = orderDao.getById(id);
            if (optionalOrder.isEmpty()) {
                throw new ServiceException("The requested order does not exist");
            }
            BookOrder shallowOrder = optionalOrder.get();

            ShallowEntityFiller<BookOrder> orderFiller = createBookOrderFiller(helper);

            BookOrder fullOrder = orderFiller.fillShallowEntity(shallowOrder);

            helper.endTransaction();
            return fullOrder;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookOrder> getOrdersForLibrarian() throws ServiceException {
        List<BookOrder> results = getOrdersOfUserId(null);
        results.sort(new OrderLibrarianPriorityComparator());
        return results;
    }

    @Override
    public List<BookOrder> getReaderOrders(Long userId) throws ServiceException {
        List<BookOrder> results = getOrdersOfUserId(userId);
        results.sort(new OrderReaderPriorityComparator());
        return results;
    }

    private List<BookOrder> getOrdersOfUserId(Long userId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookOrderDao orderDao = helper.createBookOrderDao();
            ShallowEntityFiller<BookOrder> orderFiller = createBookOrderFiller(helper);

            List<BookOrder> orderList = new ArrayList<>();
            for (BookOrder bookOrder : userId == null ? orderDao.getAll() : orderDao.getOrdersOfUser(userId)) {
                orderList.add(orderFiller.fillShallowEntity(bookOrder));
            }

            helper.endTransaction();
            return orderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private ShallowEntityFiller<BookOrder> createBookOrderFiller(DaoHelper helper) {
        UserDao userDao = helper.createUserDao();
        BookDao bookDao = helper.createBookDao();
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        ShallowEntityFiller<Book> bookFiller = fillerFactory.createBookFiller(authorDao, genreDao, publisherDao);
        return fillerFactory.createBookOrderFiller(userDao, bookDao, bookFiller);
    }

}
