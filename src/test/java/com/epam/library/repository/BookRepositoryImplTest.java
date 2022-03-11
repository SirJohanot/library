package com.epam.library.repository;

import com.epam.library.command.repository.BookRepository;
import com.epam.library.command.repository.BookRepositoryImpl;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookRepositoryImplTest {

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

    @Test
    public void testSaveShouldSaveAllObjectsRelatedToBook() throws DaoException {
        //given
        Book book = new Book(bookId, bookTitle, authorList, genre, publisher, bookPublishmentYear, bookAmount);
        Book bookThatShouldBeSaved = new Book(bookId, bookTitle, null, Genre.ofId(genreId), Publisher.ofId(publisherId), bookPublishmentYear, bookAmount);
        BookDao bookDao = Mockito.mock(BookDao.class);
        when(bookDao.getIdOfNewOrExistingObject(bookThatShouldBeSaved)).thenReturn(bookId);

        AuthorDao authorDao = Mockito.mock(AuthorDao.class);
        when(authorDao.getIdOfNewOrExistingObject(firstAuthor)).thenReturn(firstAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(firstAuthorId, bookId)).thenReturn(false);
        doNothing().when(authorDao).mapAuthorToBookInRelationTable(firstAuthorId, bookId);
        when(authorDao.getIdOfNewOrExistingObject(secondAuthor)).thenReturn(secondAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(secondAuthorId, bookId)).thenReturn(true);
        when(authorDao.getIdOfNewOrExistingObject(thirdAuthor)).thenReturn(thirdAuthorId);
        when(authorDao.isAuthorMappedToBookInRelationTable(thirdAuthorId, bookId)).thenReturn(false);

        GenreDao genreDao = Mockito.mock(GenreDao.class);
        when(genreDao.getIdOfNewOrExistingObject(genre)).thenReturn(genreId);

        PublisherDao publisherDao = Mockito.mock(PublisherDao.class);
        when(publisherDao.getIdOfNewOrExistingObject(publisher)).thenReturn(publisherId);

        BookRepository bookRepository = new BookRepositoryImpl(bookDao, authorDao, genreDao, publisherDao);
        //when
        bookRepository.save(book);
        //then
        verify(genreDao, times(1)).getIdOfNewOrExistingObject(genre);

        verify(publisherDao, times(1)).getIdOfNewOrExistingObject(publisher);

        verify(bookDao, times(1)).getIdOfNewOrExistingObject(bookThatShouldBeSaved);

        verify(authorDao, times(1)).getIdOfNewOrExistingObject(firstAuthor);
        verify(authorDao, times(1)).mapAuthorToBookInRelationTable(firstAuthorId, bookId);

        verify(authorDao, times(1)).getIdOfNewOrExistingObject(secondAuthor);
        verify(authorDao, times(0)).mapAuthorToBookInRelationTable(secondAuthorId, bookId);

        verify(authorDao, times(1)).getIdOfNewOrExistingObject(thirdAuthor);
        verify(authorDao, times(1)).mapAuthorToBookInRelationTable(thirdAuthorId, bookId);

        verify(authorDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.ID_COLUMN);
        verify(genreDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.GENRE_ID_COLUMN);
        verify(publisherDao, times(1)).deleteUnreferenced(Book.TABLE_NAME, Book.PUBLISHER_ID_COLUMN);
    }
}
