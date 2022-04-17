package com.epam.library.service;

import com.epam.library.assembler.AssemblerFactory;
import com.epam.library.assembler.BookAssembler;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.parser.AuthorsLineParser;
import com.epam.library.specification.NoSpecification;
import com.epam.library.validation.BookValidator;
import com.epam.library.validation.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private PublisherDao publisherDao;
    private DaoHelper helper;
    private DaoHelperFactory helperFactory;

    private BookAssembler bookAssembler;
    private AssemblerFactory assemblerFactory;

    private Validator<Book> bookValidator;

    private AuthorsLineParser authorsLineParser;

    private final Long bookId = 1L;
    private final String title = "title";
    private final String authorsLine = "author";
    private final List<Author> authorList = List.of(Author.ofName("author"));
    private final String genreName = "genre";
    private final Genre genre = Genre.ofName(genreName);
    private final String publisherName = "publisher";
    private final Publisher publisher = Publisher.ofName(publisherName);
    private final Year publishmentYear = Year.of(1856);
    private final int amount = 1;

    private BookServiceImpl bookService;

    @Before
    public void setUp() throws DaoException {
        bookDao = mock(BookDao.class);

        authorDao = mock(AuthorDao.class);

        genreDao = mock(GenreDao.class);

        publisherDao = mock(PublisherDao.class);

        helper = mock(DaoHelper.class);
        when(helper.createBookDao()).thenReturn(bookDao);
        when(helper.createAuthorDao()).thenReturn(authorDao);
        when(helper.createGenreDao()).thenReturn(genreDao);
        when(helper.createPublisherDao()).thenReturn(publisherDao);

        helperFactory = mock(DaoHelperFactory.class);
        when(helperFactory.createHelper()).thenReturn(helper);

        bookAssembler = mock(BookAssembler.class);

        assemblerFactory = mock(AssemblerFactory.class);
        when(assemblerFactory.createBookAssembler(bookDao, authorDao, genreDao, publisherDao)).thenReturn(bookAssembler);

        bookValidator = mock(BookValidator.class);

        authorsLineParser = mock(AuthorsLineParser.class);

        bookService = new BookServiceImpl(helperFactory, assemblerFactory, bookValidator, authorsLineParser);
    }

    @After
    public void tearDown() {
        bookDao = null;
        authorDao = null;
        genreDao = null;
        publisherDao = null;
        helper = null;
        helperFactory = null;
        bookAssembler = null;
        assemblerFactory = null;
        bookService = null;
    }

    @Test
    public void testGetAllSpecifiedBooksShouldReturnBooksReturnedByBookRepositoryWhenThereIsNoSpecification() throws DaoException, ServiceException {
        //given
        Book firstBook = new Book(bookId, title, null, genre, publisher, publishmentYear, amount);
        List<Book> expectedBookList = List.of(firstBook);
        when(bookAssembler.getAll()).thenReturn(expectedBookList);

        //when
        List<Book> actualBookList = bookService.getSpecifiedBooks(new NoSpecification<>());
        //then
        Assert.assertEquals(expectedBookList, actualBookList);
    }

    @Test
    public void testGetBookByIdShouldReturnTheBookWhenThereIsSuchBook() throws DaoException, ServiceException {
        //given
        Book expectedBook = new Book(bookId, title, null, genre, publisher, publishmentYear, amount);
        Optional<Book> expectedBookOptional = Optional.of(expectedBook);
        when(bookAssembler.getById(bookId)).thenReturn(expectedBookOptional);
        //when
        Book actualBook = bookService.getBookById(bookId);
        //then
        Assert.assertEquals(expectedBook, actualBook);
    }

    @Test(expected = ServiceException.class)
    public void testGetBookByIdShouldThrowServiceExceptionWhenThereIsNoSuchBook() throws DaoException, ServiceException {
        //given
        Optional<Book> bookOptionalReturnedByRepository = Optional.empty();
        when(bookAssembler.getById(bookId)).thenReturn(bookOptionalReturnedByRepository);
        //when
        Book actualBook = bookService.getBookById(bookId);
        //then
    }

    @Test
    public void testSaveBookShouldInvokeSaveMethodOfDaoWhenBookIsValid() throws DaoException, ServiceException, ValidationException {
        //given
        Book bookToBeSaved = new Book(bookId, title, authorList, genre, publisher, publishmentYear, amount);

        when(authorsLineParser.parse(authorsLine)).thenReturn(authorList);

        doNothing().when(bookValidator).validate(bookToBeSaved);
        //when
        bookService.saveBook(bookId, title, authorsLine, genreName, publisherName, publishmentYear, amount);
        //then
        verify(bookAssembler, times(1)).save(bookToBeSaved);
    }

    @Test(expected = ValidationException.class)
    public void testSaveBookShouldThrowValidationExceptionWhenBookIsNotValid() throws ServiceException, ValidationException {
        Book bookToBeSaved = new Book(bookId, title, authorList, genre, publisher, publishmentYear, amount);

        when(authorsLineParser.parse(authorsLine)).thenReturn(authorList);

        doThrow(new ValidationException()).when(bookValidator).validate(bookToBeSaved);
        //when
        bookService.saveBook(bookId, title, authorsLine, genreName, publisherName, publishmentYear, amount);
        //then
    }

    @Test
    public void testDeleteBookByIdShouldInvokeRemoveByIdMethodOfRepository() throws DaoException, ServiceException {
        //given
        Long bookId = 3L;
        //when
        bookService.deleteBookById(bookId);
        //then
        verify(bookAssembler, times(1)).removeById(bookId);
    }
}
