package com.epam.library.service;

import com.epam.library.command.repository.BookOrderRepository;
import com.epam.library.command.repository.RepositoryFactory;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class BookOrderServiceImpl implements BookOrderService {

    private final DaoHelperFactory daoHelperFactory;
    private final RepositoryFactory repositoryFactory;

    public BookOrderServiceImpl(DaoHelperFactory daoHelperFactory, RepositoryFactory repositoryFactory) {
        this.daoHelperFactory = daoHelperFactory;
        this.repositoryFactory = repositoryFactory;
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

            BookOrderRepository orderRepository = buildBookOrderRepository(helper);
            Optional<BookOrder> optionalBookOrder = orderRepository.getById(orderId);
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

            BookOrderRepository orderRepository = buildBookOrderRepository(helper);
            Optional<BookOrder> optionalBookOrder = orderRepository.getById(id);
            if (optionalBookOrder.isEmpty()) {
                throw new ServiceException("Could not find the requested bookOrder");
            }
            BookOrder order = optionalBookOrder.get();

            helper.endTransaction();
            return order;
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

            BookOrderRepository orderRepository = buildBookOrderRepository(helper);

            List<BookOrder> orderList = userId == null ? orderRepository.getAll() : orderRepository.getOrdersOfUser(userId);

            helper.endTransaction();
            return orderList;
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

}
