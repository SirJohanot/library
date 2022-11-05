package com.patiun.library.assembler;

import com.patiun.library.dao.BookOrderDao;
import com.patiun.library.dao.UserDao;
import com.patiun.library.entity.User;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.DaoException;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.entity.enumeration.RentalType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookOrderAssemblerImpl implements BookOrderAssembler {

    private final BookOrderDao bookOrderDao;
    private final BookAssembler bookAssembler;
    private final UserDao userDao;

    public BookOrderAssemblerImpl(BookOrderDao bookOrderDao, BookAssembler bookAssembler, UserDao userDao) {
        this.bookOrderDao = bookOrderDao;
        this.bookAssembler = bookAssembler;
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
        Long id = shallowOrder.getId();

        User shallowOrderUser = shallowOrder.getUser();
        Long shallowOrderUserId = shallowOrderUser.getId();
        Optional<User> optionalUser = userDao.getById(shallowOrderUserId);
        if (optionalUser.isEmpty()) {
            throw new DaoException("Could not find the user associated with inputted shallow bookOrder");
        }
        User user = optionalUser.get();

        Book shallowOrderBook = shallowOrder.getBook();
        Long shallowOrderBookId = shallowOrderBook.getId();
        Optional<Book> optionalBook = bookAssembler.getById(shallowOrderBookId);
        if (optionalBook.isEmpty()) {
            throw new DaoException("Could not find the book associated with inputted shallow BookOrder");
        }
        Book book = optionalBook.get();

        Date startDate = shallowOrder.getStartDate();

        Date endDate = shallowOrder.getEndDate();

        Date returnDate = shallowOrder.getReturnDate();

        RentalType type = shallowOrder.getType();

        RentalState state = shallowOrder.getState();

        return new BookOrder(id, book, user, startDate, endDate, returnDate, type, state);
    }
}
