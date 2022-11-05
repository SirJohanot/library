package com.patiun.library.assembler;

import com.patiun.library.dao.book.BookDao;
import com.patiun.library.dao.book.GenreDao;
import com.patiun.library.entity.book.Book;
import com.patiun.library.dao.book.AuthorDao;
import com.patiun.library.dao.book.PublisherDao;
import com.patiun.library.entity.book.Author;
import com.patiun.library.entity.book.Genre;
import com.patiun.library.entity.book.Publisher;
import com.patiun.library.exception.DaoException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookAssemblerImpl implements BookAssembler {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final PublisherDao publisherDao;

    public BookAssemblerImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.publisherDao = publisherDao;
    }

    @Override
    public Optional<Book> getById(Long id) throws DaoException {
        Optional<Book> optionalBook = bookDao.getById(id);
        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }

        Book shallowBook = optionalBook.get();

        return Optional.of(buildFullBook(shallowBook));
    }

    @Override
    public List<Book> getAll() throws DaoException {
        List<Book> results = new ArrayList<>();
        for (Book shallowBook : bookDao.getAll()) {
            results.add(buildFullBook(shallowBook));
        }
        return results;
    }

    @Override
    public void save(Book item) throws DaoException {
        Long id = item.getId();

        String title = item.getTitle();

        Genre genre = item.getGenre();
        Long savedGenreId = genreDao.getIdOfNewOrExistingObject(genre);
        Genre savedBookGenre = Genre.ofId(savedGenreId);

        Publisher publisher = item.getPublisher();
        Long savedPublisherId = publisherDao.getIdOfNewOrExistingObject(publisher);
        Publisher savedBookPublisher = Publisher.ofId(savedPublisherId);

        Year publishmentYear = item.getPublishmentYear();

        int amount = item.getAmount();

        Book book = new Book(id, title, null, savedBookGenre, savedBookPublisher, publishmentYear, amount);
        Long savedBookId = bookDao.getIdOfNewOrExistingObject(book);

        authorDao.deleteBookMappingsFromRelationTable(savedBookId);
        List<Author> authors = item.getAuthorList();
        saveAuthorsAndMapThemToBook(authorDao, authors, savedBookId);

        deleteUnreferenced();

    }

    @Override
    public void removeById(Long id) throws DaoException {
        bookDao.removeById(id);
    }

    @Override
    public Long getIdOfNewOrExistingObject(Book object) throws DaoException {
        return bookDao.getIdOfNewOrExistingObject(object);
    }

    @Override
    public void tweakAmount(Long bookId, int value) throws DaoException {
        bookDao.tweakAmount(bookId, value);
    }

    private Book buildFullBook(Book shallowBook) throws DaoException {
        Long id = shallowBook.getId();

        String title = shallowBook.getTitle();

        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(id);

        Genre shallowBookGenre = shallowBook.getGenre();
        Long shallowBookGenreId = shallowBookGenre.getId();
        Optional<Genre> optionalGenre = genreDao.getById(shallowBookGenreId);

        if (optionalGenre.isEmpty()) {
            throw new DaoException("Could not find the genre associated with book id");
        }

        Publisher shallowBookPublisher = shallowBook.getPublisher();
        Long shallowBookPublisherId = shallowBookPublisher.getId();
        Optional<Publisher> optionalPublisher = publisherDao.getById(shallowBookPublisherId);

        if (optionalPublisher.isEmpty()) {
            throw new DaoException("Could not find the publisher associated with book id");
        }
        Genre genre = optionalGenre.get();
        Publisher publisher = optionalPublisher.get();

        Year publishmentYear = shallowBook.getPublishmentYear();

        int amount = shallowBook.getAmount();

        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }

    private void deleteUnreferenced() throws DaoException {
        authorDao.deleteRedundant();
        genreDao.deleteRedundant();
        publisherDao.deleteRedundant();
    }

    private void saveAuthorsAndMapThemToBook(AuthorDao authorDao, List<Author> authors, Long bookId) throws DaoException {
        for (Author author : authors) {
            Long authorId = authorDao.getIdOfNewOrExistingObject(author);
            if (!authorDao.isAuthorMappedToBookInRelationTable(authorId, bookId)) {
                authorDao.mapAuthorToBookInRelationTable(authorId, bookId);
            }
        }
    }

}
