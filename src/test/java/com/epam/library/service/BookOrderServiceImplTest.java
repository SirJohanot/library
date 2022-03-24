package com.epam.library.service;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.repository.BookOrderRepository;
import com.epam.library.repository.RepositoryFactory;
import com.epam.library.validation.BookOrderValidator;
import com.epam.library.validation.Validator;
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

    private BookOrderRepository bookOrderRepository;
    private RepositoryFactory repositoryFactory;

    private Validator<BookOrder> bookOrderValidator;

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

        bookOrderRepository = mock(BookOrderRepository.class);
        repositoryFactory = mock(RepositoryFactory.class);
        when(repositoryFactory.createBookOrderRepository(bookOrderDao, userDao, bookDao, authorDao, genreDao, publisherDao)).thenReturn(bookOrderRepository);

        bookOrderValidator = mock(BookOrderValidator.class);

        bookOrderService = new BookOrderServiceImpl(helperFactory, repositoryFactory, bookOrderValidator);
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
