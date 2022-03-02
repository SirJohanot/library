package com.epam.library.service.shallowentityfiller;

import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public class ShallowBookFiller implements ShallowEntityFiller<Book> {

    @Override
    public Book fillShallowEntity(Book shallowEntity, DaoHelper daoHelper) throws DaoException, ServiceException {
        Long id = shallowEntity.getId();
        String title = shallowEntity.getTitle();
        AuthorDao authorDao = daoHelper.createAuthorDao();
        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(shallowEntity.getId());
        GenreDao genreDao = daoHelper.createGenreDao();
        Optional<Genre> optionalGenre = genreDao.getById(shallowEntity.getGenre().getId());
        PublisherDao publisherDao = daoHelper.createPublisherDao();
        Optional<Publisher> optionalPublisher = publisherDao.getById(shallowEntity.getPublisher().getId());
        if (optionalGenre.isEmpty() || optionalPublisher.isEmpty()) {
            throw new ServiceException("There is an error in database content");
        }
        Genre genre = optionalGenre.get();
        Publisher publisher = optionalPublisher.get();
        Year publishmentYear = shallowEntity.getPublishmentYear();
        int amount = shallowEntity.getAmount();
        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }
}
