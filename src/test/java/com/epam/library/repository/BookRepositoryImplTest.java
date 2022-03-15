package com.epam.library.repository;

import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
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

public class BookRepositoryImplTest {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private PublisherDao publisherDao;
    private BookRepository bookRepository;

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
        bookRepository = new BookRepositoryImpl(bookDao, authorDao, genreDao, publisherDao);
    }

    @After
    public void tearDown() {
        bookDao = null;
        authorDao = null;
        genreDao = null;
        publisherDao = null;
        bookRepository = null;
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
        List<Book> actualBooks = bookRepository.getAll();
        //then
        Assert.assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testSaveShouldSaveAShallowCopyOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        Book shallowCopy = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);

        when(genreDao.getIdOfNewOrExistingObject(genre)).thenReturn(genreId);

        when(publisherDao.getIdOfNewOrExistingObject(publisher)).thenReturn(publisherId);
        //when
        bookRepository.save(book);
        //then
        verify(bookDao, times(1)).getIdOfNewOrExistingObject(shallowCopy);
    }

    @Test
    public void testSaveShouldSaveTheGenreOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookRepository.save(book);
        //then
        verify(genreDao, times(1)).getIdOfNewOrExistingObject(genre);
    }

    @Test
    public void testSaveShouldSaveThePublisherOfTheBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookRepository.save(book);
        //then
        verify(publisherDao, times(1)).getIdOfNewOrExistingObject(publisher);
    }

    @Test
    public void testSaveShouldSaveAllAuthorsRelatedToBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        //when
        bookRepository.save(book);
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
        bookRepository.save(book);
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
        bookRepository.save(book);
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
        bookRepository.save(book);
        //then
        verify(authorDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.ID_COLUMN);
        verify(genreDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.GENRE_ID_COLUMN);
        verify(publisherDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.PUBLISHER_ID_COLUMN);
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
        Optional<Book> actualOptionalBook = bookRepository.getById(bookId);
        //then
        Assert.assertEquals(expectedOptionalBook, actualOptionalBook);
    }

    @Test
    public void testRemoveByIdShouldDelegateToBookDao() throws DaoException {
        //given
        doNothing().when(bookDao).removeById(bookId);
        //when
        bookRepository.removeById(bookId);
        //then
        verify(bookDao, times(1)).removeById(bookId);
    }

    @Test
    public void testGetIdOfNewOrExistingObjectShouldDelegateToBookDao() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);
        when(bookDao.getIdOfNewOrExistingObject(book)).thenReturn(bookId);
        //when
        Long actualBookId = bookRepository.getIdOfNewOrExistingObject(book);
        //then
        Assert.assertEquals(bookId, actualBookId);
    }

    @Test
    public void testTweakAmountShouldDelegateToBookDao() throws DaoException {
        //given
        int amountToTweakBy = 1;
        doNothing().when(bookDao).tweakAmount(bookId, amountToTweakBy);
        //when
        bookRepository.tweakAmount(bookId, amountToTweakBy);
        //then
        verify(bookDao, times(1)).tweakAmount(bookId, amountToTweakBy);
    }
}
