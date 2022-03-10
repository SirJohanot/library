package com.epam.library.command.repository;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.DaoException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookOrderRepositoryImpl implements BookOrderRepository {

    private final BookOrderDao bookOrderDao;
    private final BookRepository bookRepository;
    private final UserDao userDao;

    public BookOrderRepositoryImpl(BookOrderDao bookOrderDao, BookRepository bookRepository, UserDao userDao) {
        this.bookOrderDao = bookOrderDao;
        this.bookRepository = bookRepository;
        this.userDao = userDao;
    }

    @Override
    public void setState(Long id, RentalState newState) throws DaoException {
        bookOrderDao.setState(id, newState);
    }

    @Override
    public void setReturnDate(Long id, Date returnDate) throws DaoException {
        bookOrderDao.setReturnDate(id, returnDate);
    }

    @Override
    public List<BookOrder> getOrdersOfUser(Long userId) throws DaoException {
        List<BookOrder> orderList = new ArrayList<>();
        for (BookOrder bookOrder : bookOrderDao.getOrdersOfUser(userId)) {
            orderList.add(buildFullOrder(bookOrder));
        }
        return orderList;
    }

    @Override
    public Optional<BookOrder> getById(Long id) throws DaoException {
        Optional<BookOrder> shallowOrder = bookOrderDao.getById(id);
        if (shallowOrder.isEmpty()) {
            return Optional.empty();
        }
        BookOrder order = shallowOrder.get();

        return Optional.of(buildFullOrder(order));
    }

    @Override
    public List<BookOrder> getAll() throws DaoException {
        List<BookOrder> results = new ArrayList<>();
        for (BookOrder shallowBookOrder : bookOrderDao.getAll()) {
            results.add(buildFullOrder(shallowBookOrder));
        }
        return results;
    }

    @Override
    public void save(BookOrder item) throws DaoException {
        bookOrderDao.save(item);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        bookOrderDao.removeById(id);
    }

    @Override
    public Long getIdOfNewOrExistingObject(BookOrder object) throws DaoException {
        return bookOrderDao.getIdOfNewOrExistingObject(object);
    }

    private BookOrder buildFullOrder(BookOrder shallowOrder) throws DaoException {
        Long userId = shallowOrder.getUser().getId();
        Optional<User> optionalUser = userDao.getById(userId);
        if (optionalUser.isEmpty()) {
            throw new DaoException("Could not find a user associated with inputted shallow bookOrder");
        }
        User user = optionalUser.get();

        Book shallowOrderBook = shallowOrder.getBook();
        Long shallowOrderBookId = shallowOrderBook.getId();
        Optional<Book> optionalBook = bookRepository.getById(shallowOrderBookId);
        if (optionalBook.isEmpty()) {
            throw new DaoException("Could not find a book associated with inputted shallow BookOrder");
        }
        Book book = optionalBook.get();

        Long id = shallowOrder.getId();

        Date startDate = shallowOrder.getStartDate();

        Date endDate = shallowOrder.getEndDate();

        Date returnDate = shallowOrder.getReturnDate();

        RentalType type = shallowOrder.getType();

        RentalState state = shallowOrder.getState();

        return new BookOrder(id, book, user, startDate, endDate, returnDate, type, state);
    }
}
