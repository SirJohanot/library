package com.epam.library.service;

import com.epam.library.command.repository.BookOrderRepository;
import com.epam.library.command.repository.RepositoryFactory;
import com.epam.library.command.validation.BookOrderValidator;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookOrderServiceImplTest {

    private BookOrderDao bookOrderDao;
    private UserDao userDao;
    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private PublisherDao publisherDao;
    private DaoHelper helper;
    private DaoHelperFactory helperFactory;

    private BookOrderRepository bookOrderRepository;
    private RepositoryFactory repositoryFactory;

    private final Long userId = 3L;
    private final User user = User.ofId(userId);
    private final Long bookId = 7L;
    private final Book book = Book.ofId(bookId);
    private final Long orderId = 3L;
    private final Date startDate = Date.valueOf("1978-3-12");
    private final Date endDate = Date.valueOf("1978-3-15");
    private final RentalState state = RentalState.ORDER_PLACED;
    private final RentalType type = RentalType.OUT_OF_LIBRARY;

    private BookOrderServiceImpl bookOrderService;

    @Before
    public void setUp() throws DaoException {
        bookOrderDao = mock(BookOrderDao.class);

        userDao = mock(UserDao.class);

        bookDao = mock(BookDao.class);

        authorDao = mock(AuthorDao.class);

        genreDao = mock(GenreDao.class);

        publisherDao = mock(PublisherDao.class);

        helper = mock(DaoHelper.class);
        when(helper.createBookOrderDao()).thenReturn(bookOrderDao);
        when(helper.createUserDao()).thenReturn(userDao);
        when(helper.createBookDao()).thenReturn(bookDao);
        when(helper.createAuthorDao()).thenReturn(authorDao);
        when(helper.createGenreDao()).thenReturn(genreDao);
        when(helper.createPublisherDao()).thenReturn(publisherDao);

        helperFactory = mock(DaoHelperFactory.class);
        when(helperFactory.createHelper()).thenReturn(helper);

        bookOrderRepository = mock(BookOrderRepository.class);

        repositoryFactory = mock(RepositoryFactory.class);
        when(repositoryFactory.createBookOrderRepository(bookOrderDao, userDao, bookDao, authorDao, genreDao, publisherDao)).thenReturn(bookOrderRepository);

        bookOrderService = new BookOrderServiceImpl(helperFactory, repositoryFactory);
    }

    @After
    public void tearDown() {
        bookOrderDao = null;
        userDao = null;
        bookDao = null;
        authorDao = null;
        genreDao = null;
        publisherDao = null;
        helper = null;
        helperFactory = null;
        bookOrderRepository = null;
        repositoryFactory = null;
        bookOrderService = null;
    }

    @Test(expected = ServiceException.class)
    public void testPlaceOrderShouldThrowServiceExceptionWhenBookOrderDidNotGetValidated() throws ValidationException, ServiceException {
        //given
        BookOrder orderToBeSaved = new BookOrder(null, book, user, startDate, endDate, null, type, state);

        BookOrderValidator bookOrderValidator = mock(BookOrderValidator.class);
        doThrow(new ValidationException()).when(bookOrderValidator).validate(orderToBeSaved);
        //when
        bookOrderService.placeOrder(startDate, endDate, type, bookId, userId, bookOrderValidator);
        //then
    }

    @Test(expected = ServiceException.class)
    public void testPlaceOrderShouldThrowServiceExceptionWhenBookByBookIdDoesNotExist() throws ServiceException, DaoException {
        //given
        BookOrderValidator bookOrderValidator = mock(BookOrderValidator.class);

        when(bookDao.getById(bookId)).thenReturn(Optional.empty());
        //when
        bookOrderService.placeOrder(startDate, endDate, type, bookId, userId, bookOrderValidator);
        //then
    }

    @Test(expected = ServiceException.class)
    public void testPlaceOrderShouldThrowServiceExceptionWhenUserByUserIdDoesNotExist() throws ServiceException, DaoException {
        //given
        BookOrderValidator bookOrderValidator = mock(BookOrderValidator.class);

        when(userDao.getById(userId)).thenReturn(Optional.empty());
        //when
        bookOrderService.placeOrder(startDate, endDate, type, bookId, userId, bookOrderValidator);
        //then
    }

    @Test
    public void testPlaceOrderShouldSaveOrderWhenBookExistsAndUserExistsAndBookOrderIsValid() throws ValidationException, ServiceException, DaoException {
        //given
        BookOrder orderToBeSaved = new BookOrder(null, book, user, startDate, endDate, null, type, state);

        BookOrderValidator bookOrderValidator = mock(BookOrderValidator.class);
        doNothing().when(bookOrderValidator).validate(orderToBeSaved);

        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(bookDao.getById(bookId)).thenReturn(Optional.of(book));
        //when
        bookOrderService.placeOrder(startDate, endDate, type, bookId, userId, bookOrderValidator);
        //then
        verify(bookOrderRepository, times(1)).save(orderToBeSaved);
    }

    @Test(expected = ServiceException.class)
    public void testAdvanceOrderShouldThrowExceptionWhenOrderByIdDoesNotExist() throws DaoException, ServiceException {
        //given
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.empty());
        //when
        bookOrderService.advanceOrderState(orderId, state);
        //then
    }

    @Test
    public void testAdvanceOrderShouldSetOrderStateToNewState() throws DaoException, ServiceException {
        //given
        BookOrder bookOrder = new BookOrder(orderId, Book.ofId(bookId), null, null, null, null, null, null);
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.of(bookOrder));
        //when
        bookOrderService.advanceOrderState(orderId, state);
        //then
        verify(bookOrderRepository, times(1)).setState(orderId, state);
    }

    @Test
    public void testAdvanceOrderShouldSubtractOneFromTargetBookAmountWhenNewStateIsOrderApproved() throws DaoException, ServiceException {
        //given
        RentalState newState = RentalState.ORDER_APPROVED;

        BookOrder bookOrder = new BookOrder(orderId, Book.ofId(bookId), null, null, null, null, null, null);
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.of(bookOrder));
        //when
        bookOrderService.advanceOrderState(orderId, newState);
        //then
        verify(bookDao, times(1)).tweakAmount(bookId, -1);
        verify(bookDao, never()).tweakAmount(bookId, 1);
    }

    @Test
    public void testAdvanceOrderShouldAddOneToTargetBookAmountWhenNewStateIsBookReturned() throws DaoException, ServiceException {
        //given
        RentalState newState = RentalState.BOOK_RETURNED;

        BookOrder bookOrder = new BookOrder(orderId, Book.ofId(bookId), null, null, null, null, null, null);
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.of(bookOrder));
        //when
        bookOrderService.advanceOrderState(orderId, newState);
        //then
        verify(bookDao, never()).tweakAmount(bookId, -1);
        verify(bookDao, times(1)).tweakAmount(bookId, 1);
    }

    @Test(expected = ServiceException.class)
    public void testGetOrderByIdShouldThrowServiceExceptionWhenTheOrderCouldNotBeFound() throws ServiceException, DaoException {
        //given
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.empty());
        //when
        bookOrderService.getOrderById(orderId);
        //then
    }

    @Test
    public void testGetOrderByIdShouldReturnOrderFoundByOrderRepositoryWhenTheOrderExists() throws ServiceException, DaoException {
        //given
        BookOrder expectedBookOrder = new BookOrder(orderId, null, null, null, null, null, null, null);
        when(bookOrderRepository.getById(orderId)).thenReturn(Optional.of(expectedBookOrder));
        //when
        BookOrder actualBookOrder = bookOrderService.getOrderById(orderId);
        //then
        Assert.assertEquals(expectedBookOrder, actualBookOrder);
    }

}
