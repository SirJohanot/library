package com.company.library.service;

import com.company.library.assembler.AssemblerFactory;
import com.company.library.assembler.BookAssembler;
import com.company.library.dao.book.AuthorDao;
import com.company.library.dao.book.BookDao;
import com.company.library.dao.book.GenreDao;
import com.company.library.dao.book.PublisherDao;
import com.company.library.dao.helper.DaoHelper;
import com.company.library.dao.helper.DaoHelperFactory;
import com.company.library.entity.book.Book;
import com.company.library.exception.DaoException;
import com.company.library.exception.ServiceException;
import com.company.library.exception.ValidationException;
import com.company.library.parser.AuthorsLineParser;
import com.company.library.specification.Specification;
import com.company.library.validation.Validator;
import com.company.library.entity.book.Author;
import com.company.library.entity.book.Genre;
import com.company.library.entity.book.Publisher;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final DaoHelperFactory daoHelperFactory;
    private final AssemblerFactory assemblerFactory;
    private final Validator<Book> bookValidator;
    private final AuthorsLineParser authorsLineParser;

    public BookServiceImpl(DaoHelperFactory daoHelperFactory, AssemblerFactory assemblerFactory, Validator<Book> bookValidator, AuthorsLineParser authorsLineParser) {
        this.daoHelperFactory = daoHelperFactory;
        this.assemblerFactory = assemblerFactory;
        this.bookValidator = bookValidator;
        this.authorsLineParser = authorsLineParser;
    }

    @Override
    public List<Book> getSpecifiedBooks(Specification<Book> bookSpecification) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookAssembler bookAssembler = buildBookAssembler(helper);
            List<Book> allBooks = bookAssembler.getAll();

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

            BookAssembler bookAssembler = buildBookAssembler(helper);

            Optional<Book> optionalBook = bookAssembler.getById(id);
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

            BookAssembler bookAssembler = buildBookAssembler(helper);
            bookAssembler.save(newBook);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookById(Long bookId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            BookAssembler bookAssembler = buildBookAssembler(helper);
            bookAssembler.removeById(bookId);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BookAssembler buildBookAssembler(DaoHelper helper) {
        BookDao bookDao = helper.createBookDao();
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        return assemblerFactory.createBookAssembler(bookDao, authorDao, genreDao, publisherDao);
    }

    private Book buildBookFromParameters(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount, AuthorsLineParser authorsLineParser) {
        List<Author> authorList = authorsLineParser.parse(authors);

        Genre genreObject = Genre.ofName(genre);

        Publisher publisherObject = Publisher.ofName(publisher);

        return new Book(id, title, authorList, genreObject, publisherObject, publishmentYear, amount);
    }

}
