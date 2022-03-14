package com.epam.library.service;

import com.epam.library.command.repository.BookOrderRepository;
import com.epam.library.command.repository.RepositoryFactory;
import com.epam.library.command.validation.Validator;
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
import com.epam.library.exception.ValidationException;
import com.epam.library.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BookOrderServiceImpl implements BookOrderService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final DaoHelperFactory daoHelperFactory;
    private final RepositoryFactory repositoryFactory;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory, RepositoryFactory repositoryFactory) {
        this.daoHelperFactory = daoHelperFactory;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public BookOrder buildPreviewOrder(int numberOfDays, RentalType type) throws ServiceException {
        if (numberOfDays < 1) {
            ServiceException serviceException = new ServiceException("Number of days for rental cannot be less than 0");
            LOGGER.error(serviceException);
            throw serviceException;
        }
        LocalDate currentDate = LocalDate.now();
        Date dummyStartDate = Date.valueOf(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dummyStartDate);
        calendar.add(Calendar.DATE, numberOfDays);
        Date dummyEndDate = new Date(calendar.getTimeInMillis());
        return new BookOrder(null, null, null, dummyStartDate, dummyEndDate, null, type, null);
    }

    @Override
    public void placeOrder(Date startDate, Date endDate, RentalType rentalType, Long bookId, Long userId, Validator<BookOrder> bookOrderValidator) throws ServiceException {
        BookOrder newOrder = new BookOrder(null, Book.ofId(bookId), User.ofId(userId), startDate, endDate, null, rentalType, RentalState.ORDER_PLACED);
        try {
            bookOrderValidator.validate(newOrder);
        } catch (ValidationException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookDao bookDao = helper.createBookDao();
            if (bookDao.getById(bookId).isEmpty()) {
                ServiceException serviceException = new ServiceException("Cannot place an order on a book that does not exist");
                LOGGER.error("Book Id: " + bookId, serviceException);
                throw serviceException;
            }

            UserDao userDao = helper.createUserDao();
            if (userDao.getById(userId).isEmpty()) {
                ServiceException serviceException = new ServiceException("Cannot place an order for a user that does not exist");
                LOGGER.error("User Id: " + userId, serviceException);
                throw serviceException;
            }

            BookOrderRepository orderRepository = buildBookOrderRepository(helper);
            orderRepository.save(newOrder);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void advanceOrderState(Long orderId, RentalState newState) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookOrder order = getOrder(orderId, helper);

            Long bookId = order.getBook().getId();
            BookDao bookDao = helper.createBookDao();
            BookOrderRepository orderRepository = buildBookOrderRepository(helper);
            switch (newState) {
                case ORDER_APPROVED:
                    bookDao.tweakAmount(bookId, -1);
                    break;
                case BOOK_RETURNED:
                    Date currentDate = Date.valueOf(LocalDate.now());
                    orderRepository.setReturnDate(orderId, currentDate);
                    bookDao.tweakAmount(bookId, 1);
                    break;
            }
            orderRepository.setState(orderId, newState);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BookOrder getOrderById(Long id) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookOrder order = getOrder(id, helper);

            helper.endTransaction();
            return order;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookOrder> getSpecifiedOrders(Specification<BookOrder> bookOrderSpecification, Comparator<BookOrder> comparator) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookOrderRepository orderRepository = buildBookOrderRepository(helper);
            List<BookOrder> allOrders = orderRepository.getAll();

            helper.endTransaction();

            List<BookOrder> specifiedOrders = new ArrayList<>();
            for (BookOrder bookOrder : allOrders) {
                if (bookOrderSpecification.isSpecified(bookOrder)) {
                    specifiedOrders.add(bookOrder);
                }
            }
            specifiedOrders.sort(comparator);
            return specifiedOrders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BookOrderRepository buildBookOrderRepository(DaoHelper helper) {
        BookOrderDao bookOrderDao = helper.createBookOrderDao();
        UserDao userDao = helper.createUserDao();
        BookDao bookDao = helper.createBookDao();
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        return repositoryFactory.createBookOrderRepository(bookOrderDao, userDao, bookDao, authorDao, genreDao, publisherDao);
    }

    private BookOrder getOrder(Long orderId, DaoHelper helper) throws ServiceException, DaoException {
        BookOrderRepository orderRepository = buildBookOrderRepository(helper);
        Optional<BookOrder> optionalBookOrder = orderRepository.getById(orderId);
        if (optionalBookOrder.isEmpty()) {
            ServiceException serviceException = new ServiceException("Could not find the requested bookOrder");
            LOGGER.error("Order Id: " + orderId, serviceException);
            throw serviceException;
        }
        return optionalBookOrder.get();
    }

}
