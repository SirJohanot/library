package com.epam.library.service;

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
import com.epam.library.repository.BookRepository;
import com.epam.library.repository.RepositoryFactory;
import com.epam.library.specification.Specification;
import com.epam.library.validation.Validator;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final DaoHelperFactory daoHelperFactory;
    private final RepositoryFactory repositoryFactory;
    private final Validator<Book> bookValidator;
    private final AuthorsLineParser authorsLineParser;

    public BookServiceImpl(DaoHelperFactory daoHelperFactory, RepositoryFactory repositoryFactory, Validator<Book> bookValidator, AuthorsLineParser authorsLineParser) {
        this.daoHelperFactory = daoHelperFactory;
        this.repositoryFactory = repositoryFactory;
        this.bookValidator = bookValidator;
        this.authorsLineParser = authorsLineParser;
    }

    @Override
    public List<Book> getSpecifiedBooks(Specification<Book> bookSpecification) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookRepository bookRepository = buildBookRepository(helper);
            List<Book> allBooks = bookRepository.getAll();

            List<Book> specifiedBooks = new ArrayList<>();
            for (Book book : allBooks) {
                if (bookSpecification.isSpecified(book)) {
                    specifiedBooks.add(book);
                }
            }
            helper.endTransaction();
            return specifiedBooks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getBookById(Long id) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookRepository bookRepository = buildBookRepository(helper);

            Optional<Book> optionalBook = bookRepository.getById(id);
            if (optionalBook.isEmpty()) {
                throw new ServiceException("Could not find the requested book");
            }
            Book book = optionalBook.get();

            helper.endTransaction();
            return book;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveBook(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount) throws ServiceException, ValidationException {
        Book newBook = buildBookFromParameters(id, title, authors, genre, publisher, publishmentYear, amount, authorsLineParser);
        bookValidator.validate(newBook);
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookRepository bookRepository = buildBookRepository(helper);
            bookRepository.save(newBook);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookById(Long bookId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookRepository bookRepository = buildBookRepository(helper);
            bookRepository.removeById(bookId);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BookRepository buildBookRepository(DaoHelper helper) {
        BookDao bookDao = helper.createBookDao();
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        return repositoryFactory.createBookRepository(bookDao, authorDao, genreDao, publisherDao);
    }

    private Book buildBookFromParameters(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount, AuthorsLineParser authorsLineParser) {
        List<Author> authorList = authorsLineParser.parse(authors);

        Genre genreObject = Genre.ofName(genre);

        Publisher publisherObject = Publisher.ofName(publisher);

        return new Book(id, title, authorList, genreObject, publisherObject, publishmentYear, amount);
    }

}
