package com.company.library.service;

import com.company.library.assembler.AssemblerFactory;
import com.company.library.dao.BookOrderDao;
import com.company.library.dao.UserDao;
import com.company.library.dao.book.BookDao;
import com.company.library.dao.book.GenreDao;
import com.company.library.dao.helper.DaoHelper;
import com.company.library.dao.helper.DaoHelperFactory;
import com.company.library.entity.User;
import com.company.library.entity.book.Book;
import com.company.library.entity.enumeration.UserRole;
import com.company.library.exception.DaoException;
import com.company.library.exception.ServiceException;
import com.company.library.specification.Specification;
import com.company.library.validation.Validator;
import com.company.library.assembler.BookOrderAssembler;
import com.company.library.dao.book.AuthorDao;
import com.company.library.dao.book.PublisherDao;
import com.company.library.entity.BookOrder;
import com.company.library.entity.enumeration.RentalState;
import com.company.library.entity.enumeration.RentalType;
import com.company.library.exception.ValidationException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;
    private final AssemblerFactory assemblerFactory;
    private final Validator<BookOrder> bookOrderValidator;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory, AssemblerFactory assemblerFactory, Validator<BookOrder> bookOrderValidator) {
        this.daoHelperFactory = daoHelperFactory;
        this.assemblerFactory = assemblerFactory;
        this.bookOrderValidator = bookOrderValidator;
    }

    @Override
    public void placeOrder(int numberOfDays, RentalType rentalType, Long bookId, Long userId) throws ServiceException, ValidationException {
        if (numberOfDays < 0) {
            throw new ServiceException("Number of days for rental cannot be less than 0");
        }

        if (rentalType == RentalType.TO_READING_HALL) {
            numberOfDays = 0;
        }

        Date startDate = getCurrentDate();
        Date endDate = getDateForwardedByDays(startDate, numberOfDays);
        BookOrder newOrder = new BookOrder(null, Book.ofId(bookId), User.ofId(userId), startDate, endDate, null, rentalType, RentalState.ORDER_PLACED);
        bookOrderValidator.validate(newOrder);

        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookDao bookDao = helper.createBookDao();
            Optional<Book> targetBookOptional = bookDao.getById(bookId);
            if (targetBookOptional.isEmpty()) {
                throw new ServiceException("Cannot place an order on a book that does not exist");
            }
            Book targetBook = targetBookOptional.get();
            if (targetBook.getAmount() <= 0) {
                throw new ServiceException("The requested book is not in stock");
            }

            UserDao userDao = helper.createUserDao();
            if (userDao.getById(userId).isEmpty()) {
                throw new ServiceException("Cannot place an order for a user that does not exist");
            }

            BookOrderAssembler orderRepository = buildBookOrderAssembler(helper);
            orderRepository.save(newOrder);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void advanceOrderState(Long orderId, Long userId, RentalState newState) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookOrder order = getOrder(orderId, helper);

            RentalState oldState = order.getState();
            throwExceptionIfTheNewStateCannotChangeTheOldState(oldState, newState);

            Book orderBook = order.getBook();
            Long bookId = orderBook.getId();
            BookDao bookDao = helper.createBookDao();
            UserDao userDao = helper.createUserDao();
            BookOrderAssembler orderRepository = buildBookOrderAssembler(helper);
            switch (newState) {
                case ORDER_APPROVED:
                    throwExceptionIfTheUserDoesNotExistOrIsNotOfRole(userId, UserRole.LIBRARIAN, userDao);
                    if (orderBook.getAmount() <= 0) {
                        throw new ServiceException("Cannot approve order on a book that is not in stock");
                    }

                    bookDao.tweakAmount(bookId, -1);
                    break;
                case ORDER_DECLINED:
                    throwExceptionIfTheUserDoesNotExistOrIsNotOfRole(userId, UserRole.LIBRARIAN, userDao);
                    break;
                case BOOK_COLLECTED:
                    throwExceptionIfTheUserDoesNotExistOrIsNotOfRole(userId, UserRole.READER, userDao);
                    throwExceptionIfTheOrderDoesNotBelongToUser(order, userId);
                case BOOK_RETURNED:
                    throwExceptionIfTheUserDoesNotExistOrIsNotOfRole(userId, UserRole.READER, userDao);
                    throwExceptionIfTheOrderDoesNotBelongToUser(order, userId);
                    Date currentDate = getCurrentDate();
                    orderRepository.setReturnDate(orderId, currentDate);
                    bookDao.tweakAmount(bookId, 1);
                    break;
                default:
                    throw new UnsupportedOperationException("Cannot advance order to " + newState);
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

            BookOrderAssembler orderRepository = buildBookOrderAssembler(helper);
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

    private BookOrderAssembler buildBookOrderAssembler(DaoHelper helper) {
        BookOrderDao bookOrderDao = helper.createBookOrderDao();
        UserDao userDao = helper.createUserDao();
        BookDao bookDao = helper.createBookDao();
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        return assemblerFactory.createBookOrderAssembler(bookOrderDao, userDao, bookDao, authorDao, genreDao, publisherDao);
    }

    private Date getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return Date.valueOf(currentDate);
    }

    private Date getDateForwardedByDays(Date startDate, int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, numberOfDays);
        return new Date(calendar.getTimeInMillis());
    }

    private void throwExceptionIfTheNewStateCannotChangeTheOldState(RentalState oldState, RentalState newState) throws ServiceException {
        switch (oldState) {
            case ORDER_PLACED:
                if (newState == RentalState.ORDER_APPROVED || newState == RentalState.ORDER_DECLINED) {
                    return;
                }
            case BOOK_COLLECTED:
                if (newState == RentalState.BOOK_RETURNED) {
                    return;
                }
            case ORDER_APPROVED:
                if (newState == RentalState.BOOK_COLLECTED) {
                    return;
                }
        }
        throw new ServiceException("Order with state: " + oldState + " cannot be changed to " + newState);
    }

    private void throwExceptionIfTheOrderDoesNotBelongToUser(BookOrder order, Long userId) throws ServiceException {
        User orderUser = order.getUser();
        Long orderUserId = orderUser.getId();
        if (!userId.equals(orderUserId)) {
            throw new ServiceException("The Order does not belong to the user");
        }
    }

    private void throwExceptionIfTheUserDoesNotExistOrIsNotOfRole(Long userId, UserRole requiredRole, UserDao userDao) throws DaoException, ServiceException {
        Optional<User> optionalUser = userDao.getById(userId);
        if (optionalUser.isEmpty()) {
            throw new ServiceException("The user does not exist in the database");
        }
        User user = optionalUser.get();
        UserRole userRole = user.getRole();
        if (userRole != requiredRole) {
            throw new ServiceException("The user has to be a " + requiredRole + " to perform the action");
        }
    }

    private BookOrder getOrder(Long orderId, DaoHelper helper) throws ServiceException, DaoException {
        BookOrderAssembler orderRepository = buildBookOrderAssembler(helper);
        Optional<BookOrder> optionalBookOrder = orderRepository.getById(orderId);
        if (optionalBookOrder.isEmpty()) {
            throw new ServiceException("Could not find the requested bookOrder");
        }
        return optionalBookOrder.get();
    }

}
