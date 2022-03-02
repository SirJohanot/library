package com.epam.library.service.shallowentityfiller;

import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.sql.Date;
import java.util.Optional;

public class ShallowBookOrderFiller implements ShallowEntityFiller<BookOrder> {

    private final ShallowEntityFiller<Book> bookFiller;

    public ShallowBookOrderFiller(ShallowEntityFiller<Book> bookFiller) {
        this.bookFiller = bookFiller;
    }

    @Override
    public BookOrder fillShallowEntity(BookOrder shallowEntity, DaoHelper daoHelper) throws DaoException, ServiceException {
        Long userId = shallowEntity.getUser().getId();
        UserDao userDao = daoHelper.createUserDao();
        Optional<User> optionalUser = userDao.getById(userId);
        if (optionalUser.isEmpty()) {
            throw new ServiceException("Could not find a user associated with inputted shallow bookOrder");
        }
        User user = optionalUser.get();
        Long bookId = shallowEntity.getBook().getId();
        BookDao bookDao = daoHelper.createBookDao();
        Optional<Book> shallowBook = bookDao.getById(bookId);
        if (shallowBook.isEmpty()) {
            throw new ServiceException("Could not find a book associated with inputted shallow BookOrder");
        }
        Book filledBook = bookFiller.fillShallowEntity(shallowBook.get(), daoHelper);
        Long id = shallowEntity.getId();
        Date startDate = shallowEntity.getStartDate();
        Date endDate = shallowEntity.getEndDate();
        Date returnDate = shallowEntity.getReturnDate();
        RentalType type = shallowEntity.getType();
        RentalState state = shallowEntity.getState();
        return new BookOrder(id, filledBook, user, startDate, endDate, returnDate, type, state);
    }
}