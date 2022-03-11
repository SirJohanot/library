package com.epam.library.repository;

import com.epam.library.command.repository.BookOrderRepository;
import com.epam.library.command.repository.BookOrderRepositoryImpl;
import com.epam.library.command.repository.BookRepository;
import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.DaoException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookOrderRepositoryImplTest {

    private UserDao userDao;
    private BookOrderDao bookOrderDao;
    private BookRepository bookRepository;
    private BookOrderRepository bookOrderRepository;

    private final Long bookId = 3L;
    private final String bookTitle = "Good Book";
    private final Year bookPublishmentYear = Year.of(1984);
    private final Integer bookAmount = 3;
    private final Book book = new Book(bookId, bookTitle, null, null, null, bookPublishmentYear, bookAmount);

    private final Long userId = 5L;
    private final String userLogin = "user";
    private final String userName = "usa";
    private final String userSurname = "smith";
    private final UserRole userRole = UserRole.READER;
    private final boolean userBlocked = false;
    private final User user = new User(userId, userLogin, userName, userSurname, userRole, userBlocked);

    private final Long orderId = 8L;
    private final Date startDate = Date.valueOf("2022-3-11");
    private final Date endDate = Date.valueOf("2022-3-16");
    private final Date returnDate = Date.valueOf("2022-3-15");
    private final RentalType rentalType = RentalType.ON_SUBSCRIPTION;
    private final RentalState rentalState = RentalState.BOOK_RETURNED;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
        bookOrderDao = mock(BookOrderDao.class);
        bookRepository = mock(BookRepository.class);
        bookOrderRepository = new BookOrderRepositoryImpl(bookOrderDao, bookRepository, userDao);
    }

    @After
    public void tearDown() {
        userDao = null;
        bookOrderDao = null;
        bookRepository = null;
        bookOrderRepository = null;
    }

    @Test
    public void testSetStateShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderRepository.setState(orderId, rentalState);
        //then
        verify(bookOrderDao, times(1)).setState(orderId, rentalState);
    }

    @Test
    public void testSetReturnDateShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderRepository.setReturnDate(orderId, returnDate);
        //then
        verify(bookOrderDao, times(1)).setReturnDate(orderId, returnDate);
    }

    @Test
    public void testGetAllShouldReturnAListOfFullOrders() throws DaoException {
        //given
        List<BookOrder> expectedOrders = List.of(new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState));
        List<BookOrder> shallowList = List.of(new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState));
        when(bookOrderDao.getAll()).thenReturn(shallowList);
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.getById(bookId)).thenReturn(Optional.of(book));
        //when
        List<BookOrder> actualOrders = bookOrderRepository.getAll();
        //then
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testGetByIdShouldReturnFullOrder() throws DaoException {
        //given
        Optional<BookOrder> expectedOptionalOrder = Optional.of(new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState));
        BookOrder shallowOrder = new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState);
        when(bookOrderDao.getById(orderId)).thenReturn(Optional.of(shallowOrder));
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.getById(bookId)).thenReturn(Optional.of(book));
        //when
        Optional<BookOrder> actualOptionalOrder = bookOrderRepository.getById(orderId);
        //then
        Assert.assertEquals(expectedOptionalOrder, actualOptionalOrder);
    }

    @Test
    public void testGetOrdersOfUserShouldReturnAListOfFullOrders() throws DaoException {
        //given
        List<BookOrder> expectedOrders = List.of(new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState));
        List<BookOrder> shallowList = List.of(new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState));
        when(bookOrderDao.getOrdersOfUser(userId)).thenReturn(shallowList);
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.getById(bookId)).thenReturn(Optional.of(book));
        //when
        List<BookOrder> actualOrders = bookOrderRepository.getOrdersOfUser(userId);
        //then
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testSaveShouldDelegateToBookOrderDao() throws DaoException {
        //given
        BookOrder order = new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderRepository.save(order);
        //then
        verify(bookOrderDao, times(1)).save(order);
    }

    @Test
    public void testRemoveByIdShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderRepository.removeById(orderId);
        //then
        verify(bookOrderDao, times(1)).removeById(orderId);
    }

    @Test
    public void testGetIdOfNewOrExistingObjectShouldDelegateToBookOrderDao() throws DaoException {
        //given
        BookOrder order = new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderRepository.getIdOfNewOrExistingObject(order);
        //then
        verify(bookOrderDao, times(1)).getIdOfNewOrExistingObject(order);
    }
}
