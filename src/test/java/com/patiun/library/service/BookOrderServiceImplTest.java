package com.patiun.library.service;

import com.patiun.library.assembler.AssemblerFactory;
import com.patiun.library.assembler.BookOrderAssembler;
import com.patiun.library.dao.BookOrderDao;
import com.patiun.library.dao.UserDao;
import com.patiun.library.dao.book.AuthorDao;
import com.patiun.library.dao.book.BookDao;
import com.patiun.library.dao.book.GenreDao;
import com.patiun.library.dao.book.PublisherDao;
import com.patiun.library.dao.helper.DaoHelper;
import com.patiun.library.dao.helper.DaoHelperFactory;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.exception.DaoException;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.validation.BookOrderValidator;
import com.patiun.library.validation.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookOrderServiceImplTest {

    private BookOrderDao bookOrderDao;
    private UserDao userDao;
    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private PublisherDao publisherDao;
    private DaoHelper helper;
    private DaoHelperFactory helperFactory;

    private BookOrderAssembler bookOrderAssembler;
    private AssemblerFactory assemblerFactory;

    Validator<BookOrder> bookOrderValidator;

    private final Long userId = 3L;
    private final Long bookId = 7L;
    private final Long orderId = 3L;
    private final RentalState state = RentalState.ORDER_PLACED;

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

        bookOrderAssembler = mock(BookOrderAssembler.class);
        assemblerFactory = mock(AssemblerFactory.class);
        when(assemblerFactory.createBookOrderAssembler(bookOrderDao, userDao, bookDao, authorDao, genreDao, publisherDao)).thenReturn(bookOrderAssembler);

        bookOrderValidator = mock(BookOrderValidator.class);

        bookOrderService = new BookOrderServiceImpl(helperFactory, assemblerFactory, bookOrderValidator);
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
        bookOrderAssembler = null;
        assemblerFactory = null;
        bookOrderService = null;
    }

    @Test(expected = ServiceException.class)
    public void testGetOrderByIdShouldThrowServiceExceptionWhenTheOrderCouldNotBeFound() throws ServiceException, DaoException {
        //given
        when(bookOrderAssembler.getById(orderId)).thenReturn(Optional.empty());
        //when
        bookOrderService.getOrderById(orderId);
        //then
    }

    @Test
    public void testGetOrderByIdShouldReturnOrderFoundByOrderRepositoryWhenTheOrderExists() throws ServiceException, DaoException {
        //given
        BookOrder expectedBookOrder = new BookOrder(orderId, null, null, null, null, null, null, null);
        when(bookOrderAssembler.getById(orderId)).thenReturn(Optional.of(expectedBookOrder));
        //when
        BookOrder actualBookOrder = bookOrderService.getOrderById(orderId);
        //then
        Assert.assertEquals(expectedBookOrder, actualBookOrder);
    }

}
