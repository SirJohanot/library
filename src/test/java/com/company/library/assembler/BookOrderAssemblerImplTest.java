package com.company.library.assembler;

import com.company.library.dao.BookOrderDao;
import com.company.library.dao.UserDao;
import com.company.library.entity.User;
import com.company.library.entity.book.Book;
import com.company.library.entity.enumeration.UserRole;
import com.company.library.exception.DaoException;
import com.company.library.entity.BookOrder;
import com.company.library.entity.enumeration.RentalState;
import com.company.library.entity.enumeration.RentalType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookOrderAssemblerImplTest {

    private UserDao userDao;
    private BookOrderDao bookOrderDao;
    private BookAssembler bookAssembler;
    private BookOrderAssembler bookOrderAssembler;

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
    private final RentalType rentalType = RentalType.OUT_OF_LIBRARY;
    private final RentalState rentalState = RentalState.BOOK_RETURNED;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
        bookOrderDao = mock(BookOrderDao.class);
        bookAssembler = mock(BookAssembler.class);
        bookOrderAssembler = new BookOrderAssemblerImpl(bookOrderDao, bookAssembler, userDao);
    }

    @After
    public void tearDown() {
        userDao = null;
        bookOrderDao = null;
        bookAssembler = null;
        bookOrderAssembler = null;
    }

    @Test
    public void testSetStateShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderAssembler.setState(orderId, rentalState);
        //then
        verify(bookOrderDao, times(1)).setState(orderId, rentalState);
    }

    @Test
    public void testSetReturnDateShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderAssembler.setReturnDate(orderId, returnDate);
        //then
        verify(bookOrderDao, times(1)).setReturnDate(orderId, returnDate);
    }

    @Test
    public void testGetByIdShouldReturnFullOrder() throws DaoException {
        //given
        Optional<BookOrder> expectedOptionalOrder = Optional.of(new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState));
        BookOrder shallowOrder = new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState);
        when(bookOrderDao.getById(orderId)).thenReturn(Optional.of(shallowOrder));
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookAssembler.getById(bookId)).thenReturn(Optional.of(book));
        //when
        Optional<BookOrder> actualOptionalOrder = bookOrderAssembler.getById(orderId);
        //then
        Assert.assertEquals(expectedOptionalOrder, actualOptionalOrder);
    }

    @Test
    public void testGetByIdShouldReturnOptionalEmptyWhenTheOrderCouldNotBeFound() throws DaoException {
        //given
        Optional<BookOrder> expectedOptionalOrder = Optional.empty();
        when(bookOrderDao.getById(orderId)).thenReturn(Optional.empty());
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookAssembler.getById(bookId)).thenReturn(Optional.of(book));
        //when
        Optional<BookOrder> actualOptionalOrder = bookOrderAssembler.getById(orderId);
        //then
        Assert.assertEquals(expectedOptionalOrder, actualOptionalOrder);
    }

    @Test(expected = DaoException.class)
    public void testGetByIdShouldThrowDaoExceptionIfTheAssociatedBookCouldNotBeFound() throws DaoException {
        //given
        BookOrder shallowOrder = new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState);
        when(bookOrderDao.getById(orderId)).thenReturn(Optional.of(shallowOrder));
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookAssembler.getById(bookId)).thenReturn(Optional.empty());
        //when
        bookOrderAssembler.getById(orderId);
        //then
    }

    @Test
    public void testGetAllShouldReturnAListOfFullOrders() throws DaoException {
        //given
        List<BookOrder> expectedOrders = List.of(new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState));
        List<BookOrder> shallowList = List.of(new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState));
        when(bookOrderDao.getAll()).thenReturn(shallowList);
        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookAssembler.getById(bookId)).thenReturn(Optional.of(book));
        //when
        List<BookOrder> actualOrders = bookOrderAssembler.getAll();
        //then
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test(expected = DaoException.class)
    public void testGetAllShouldThrowDaoExceptionIfOneOfTheAssociatedUsersCouldNotBeFound() throws DaoException {
        //given
        List<BookOrder> shallowList = List.of(new BookOrder(orderId, Book.ofId(bookId), User.ofId(userId), startDate, endDate, returnDate, rentalType, rentalState));
        when(bookOrderDao.getAll()).thenReturn(shallowList);
        when(userDao.getById(userId)).thenReturn(Optional.empty());
        when(bookAssembler.getById(bookId)).thenReturn(Optional.of(book));
        //when
        bookOrderAssembler.getAll();
        //then
    }

    @Test
    public void testSaveShouldDelegateToBookOrderDao() throws DaoException {
        //given
        BookOrder order = new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderAssembler.save(order);
        //then
        verify(bookOrderDao, times(1)).save(order);
    }

    @Test
    public void testRemoveByIdShouldDelegateToBookOrderDao() throws DaoException {
        //given
        //when
        bookOrderAssembler.removeById(orderId);
        //then
        verify(bookOrderDao, times(1)).removeById(orderId);
    }

    @Test
    public void testGetIdOfNewOrExistingObjectShouldDelegateToBookOrderDao() throws DaoException {
        //given
        BookOrder order = new BookOrder(orderId, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderAssembler.getIdOfNewOrExistingObject(order);
        //then
        verify(bookOrderDao, times(1)).getIdOfNewOrExistingObject(order);
    }
}
