package com.patiun.library.assembler;

import com.patiun.library.dao.book.BookDao;
import com.patiun.library.dao.book.GenreDao;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.DaoException;
import com.patiun.library.dao.book.AuthorDao;
import com.patiun.library.dao.book.PublisherDao;
import com.patiun.library.entity.book.Author;
import com.patiun.library.entity.book.Genre;
import com.patiun.library.entity.book.Publisher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookAssemblerImplTest {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private PublisherDao publisherDao;
    private BookAssembler bookAssembler;

    private final Long bookId = 3L;
    private final String bookTitle = "Good Book";
    private final Year bookPublishmentYear = Year.of(1984);
    private final Integer bookAmount = 3;

    private final Long firstAuthorId = 3L;
    private final String firstAuthorName = "Tolstoy";
    private final Author firstAuthor = new Author(firstAuthorId, firstAuthorName);

    private final Long secondAuthorId = 5L;
    private final String secondAuthorName = "Dostoevsky";
    private final Author secondAuthor = new Author(secondAuthorId, secondAuthorName);

    private final Long thirdAuthorId = 7L;
    private final String thirdAuthorName = "Orwell";
    private final Author thirdAuthor = new Author(thirdAuthorId, thirdAuthorName);

    private final List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor);

    private final Long genreId = 2L;
    private final String genreName = "thriller";
    private final Genre genre = new Genre(genreId, genreName);

    private final Long publisherId = 4L;
    private final String publisherName = "me";
    private final Publisher publisher = new Publisher(publisherId, publisherName);

    @Before
    public void setUp() {
        bookDao = Mockito.mock(BookDao.class);
        authorDao = Mockito.mock(AuthorDao.class);
        genreDao = Mockito.mock(GenreDao.class);
        publisherDao = Mockito.mock(PublisherDao.class);
        bookAssembler = new BookAssemblerImpl(bookDao, authorDao, genreDao, publisherDao);
    }

    @After
    public void tearDown() {
        bookDao = null;
        authorDao = null;
        genreDao = null;
        publisherDao = null;
        bookAssembler = null;
    }

    @Test
    public void testGetByIdShouldReturnAFullBookWhenThereAreObjectsRelatedToIt() throws DaoException {
        //given
        Book bookThatHasToBeReturnedByBookDao = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);
        when(bookDao.getById(bookId)).thenReturn(Optional.of(bookThatHasToBeReturnedByBookDao));

        when(authorDao.getAuthorsAssociatedWithBookId(bookId)).thenReturn(authorList);

        when(genreDao.getById(genreId)).thenReturn(Optional.of(genre));

        when(publisherDao.getById(publisherId)).thenReturn(Optional.of(publisher));

        Optional<Book> expectedOptionalBook = Optional.of(new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount));
        //when
        Optional<Book> actualOptionalBook = bookAssembler.getById(bookId);
        //then
        Assert.assertEquals(expectedOptionalBook, actualOptionalBook);
    }

    @Test
    public void testGetByIdShouldReturnOptionalEmptyWhenTheBookCouldNotBeFound() throws DaoException {
        //given
        when(bookDao.getById(bookId)).thenReturn(Optional.empty());

        when(authorDao.getAuthorsAssociatedWithBookId(bookId)).thenReturn(authorList);

        when(genreDao.getById(genreId)).thenReturn(Optional.of(genre));

        when(publisherDao.getById(publisherId)).thenReturn(Optional.of(publisher));

        Optional<Book> expectedOptionalBook = Optional.empty();
        //when
        Optional<Book> actualOptionalBook = bookAssembler.getById(bookId);
        //then
        Assert.assertEquals(expectedOptionalBook, actualOptionalBook);
    }

    @Test(expected = DaoException.class)
    public void testGetByIdShouldThrowDaoExceptionWhenAssociatedGenreCouldNotBeFound() throws DaoException {
        //given
        Book bookThatHasToBeReturnedByBookDao = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);
        when(bookDao.getById(bookId)).thenReturn(Optional.of(bookThatHasToBeReturnedByBookDao));

        when(authorDao.getAuthorsAssociatedWithBookId(bookId)).thenReturn(authorList);

        when(genreDao.getById(genreId)).thenReturn(Optional.empty());

        when(publisherDao.getById(publisherId)).thenReturn(Optional.of(publisher));

        Optional<Book> expectedOptionalBook = Optional.of(new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount));
        //when
        bookAssembler.getById(bookId);
        //then
    }

    @Test
    public void testGetAllShouldReturnAListOfFullBooks() throws DaoException {
        //given
        List<Book> booksThatHaveToBeReturnedByBookDao = List.of(new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount));
        when(bookDao.getAll()).thenReturn(booksThatHaveToBeReturnedByBookDao);

        when(authorDao.getAuthorsAssociatedWithBookId(bookId)).thenReturn(authorList);

        when(genreDao.getById(genreId)).thenReturn(Optional.of(genre));

        when(publisherDao.getById(publisherId)).thenReturn(Optional.of(publisher));

        List<Book> expectedBooks = List.of(new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount));
        //when
        List<Book> actualBooks = bookAssembler.getAll();
        //then
        Assert.assertEquals(expectedBooks, actualBooks);
    }

    @Test(expected = DaoException.class)
    public void testGetAllShouldThrowDaoExceptionWhenOneOfTheAssociatedPublishersCouldNotBeFound() throws DaoException {
        //given
        List<Book> booksThatHaveToBeReturnedByBookDao = List.of(new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount));
        when(bookDao.getAll()).thenReturn(booksThatHaveToBeReturnedByBookDao);

        when(authorDao.getAuthorsAssociatedWithBookId(bookId)).thenReturn(authorList);

        when(genreDao.getById(genreId)).thenReturn(Optional.of(genre));

        when(publisherDao.getById(publisherId)).thenReturn(Optional.empty());

        List<Book> expectedBooks = List.of(new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount));
        //when
        bookAssembler.getAll();
        //then
    }

    @Test
    public void testSaveShouldSaveAShallowCopyOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        Book shallowCopy = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);

        when(genreDao.getIdOfNewOrExistingObject(genre)).thenReturn(genreId);

        when(publisherDao.getIdOfNewOrExistingObject(publisher)).thenReturn(publisherId);
        //when
        bookAssembler.save(book);
        //then
        verify(bookDao, times(1)).getIdOfNewOrExistingObject(shallowCopy);
    }

    @Test
    public void testSaveShouldSaveTheGenreOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookAssembler.save(book);
        //then
        verify(genreDao, times(1)).getIdOfNewOrExistingObject(genre);
    }

    @Test
    public void testSaveShouldSaveThePublisherOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookAssembler.save(book);
        //then
        verify(publisherDao, times(1)).getIdOfNewOrExistingObject(publisher);
    }

    @Test
    public void testSaveShouldSaveAllAuthorsRelatedToBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookAssembler.save(book);
        //then
        verify(authorDao, times(1)).getIdOfNewOrExistingObject(firstAuthor);

        verify(authorDao, times(1)).getIdOfNewOrExistingObject(secondAuthor);

        verify(authorDao, times(1)).getIdOfNewOrExistingObject(thirdAuthor);
    }

    @Test
    public void testSaveShouldUnmapAllAuthorsFromBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        Book shallowCopy = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);

        when(genreDao.getIdOfNewOrExistingObject(genre)).thenReturn(genreId);

        when(publisherDao.getIdOfNewOrExistingObject(publisher)).thenReturn(publisherId);

        when(authorDao.getIdOfNewOrExistingObject(firstAuthor)).thenReturn(firstAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(firstAuthorId, bookId)).thenReturn(false);
        when(authorDao.getIdOfNewOrExistingObject(secondAuthor)).thenReturn(secondAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(secondAuthorId, bookId)).thenReturn(true);
        when(authorDao.getIdOfNewOrExistingObject(thirdAuthor)).thenReturn(thirdAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(thirdAuthorId, bookId)).thenReturn(false);

        when(bookDao.getIdOfNewOrExistingObject(shallowCopy)).thenReturn(bookId);
        //when
        bookAssembler.save(book);
        //then
        verify(authorDao, times(1)).deleteBookMappingsFromRelationTable(bookId);
    }

    @Test
    public void testSaveShouldMapAllAuthorsRelatedToBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        Book shallowCopy = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);

        when(genreDao.getIdOfNewOrExistingObject(genre)).thenReturn(genreId);

        when(publisherDao.getIdOfNewOrExistingObject(publisher)).thenReturn(publisherId);

        when(authorDao.getIdOfNewOrExistingObject(firstAuthor)).thenReturn(firstAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(firstAuthorId, bookId)).thenReturn(false);
        when(authorDao.getIdOfNewOrExistingObject(secondAuthor)).thenReturn(secondAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(secondAuthorId, bookId)).thenReturn(true);
        when(authorDao.getIdOfNewOrExistingObject(thirdAuthor)).thenReturn(thirdAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(thirdAuthorId, bookId)).thenReturn(false);

        when(bookDao.getIdOfNewOrExistingObject(shallowCopy)).thenReturn(bookId);
        //when
        bookAssembler.save(book);
        //then
        verify(authorDao, times(1)).mapAuthorToBookInRelationTable(firstAuthorId, bookId);
        verify(authorDao, never()).mapAuthorToBookInRelationTable(secondAuthorId, bookId);
        verify(authorDao, times(1)).mapAuthorToBookInRelationTable(thirdAuthorId, bookId);
    }

    @Test
    public void testSaveShouldRemoveUnreferencedObjectsAfterSaving() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookAssembler.save(book);
        //then
        verify(authorDao, times(1)).deleteRedundant();
        verify(genreDao, times(1)).deleteRedundant();
        verify(publisherDao, times(1)).deleteRedundant();
    }

    @Test
    public void testRemoveByIdShouldDelegateToBookDao() throws DaoException {
        //given
        doNothing().when(bookDao).removeById(bookId);
        //when
        bookAssembler.removeById(bookId);
        //then
        verify(bookDao, times(1)).removeById(bookId);
    }

    @Test
    public void testGetIdOfNewOrExistingObjectShouldDelegateToBookDao() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);
        when(bookDao.getIdOfNewOrExistingObject(book)).thenReturn(bookId);
        //when
        Long actualBookId = bookAssembler.getIdOfNewOrExistingObject(book);
        //then
        Assert.assertEquals(bookId, actualBookId);
    }

    @Test
    public void testTweakAmountShouldDelegateToBookDao() throws DaoException {
        //given
        int amountToTweakBy = 1;
        doNothing().when(bookDao).tweakAmount(bookId, amountToTweakBy);
        //when
        bookAssembler.tweakAmount(bookId, amountToTweakBy);
        //then
        verify(bookDao, times(1)).tweakAmount(bookId, amountToTweakBy);
    }
}
