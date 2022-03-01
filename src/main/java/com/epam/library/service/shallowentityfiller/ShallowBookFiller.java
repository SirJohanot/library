package com.epam.library.service.shallowentityfiller;

import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;

import java.time.Year;
import java.util.List;

public class ShallowBookFiller implements ShallowEntityFiller<Book> {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final PublisherDao publisherDao;

    public ShallowBookFiller(AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.publisherDao = publisherDao;
    }

    @Override
    public Book fillShallowEntity(Book shallowEntity) throws DaoException {
        Long id = shallowEntity.getId();
        String title = shallowEntity.getTitle();
        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(shallowEntity.getId());
        Genre genre = genreDao.getById(shallowEntity.getGenre().getId()).get();
        Publisher publisher = publisherDao.getById(shallowEntity.getPublisher().getId()).get();
        Year publishmentYear = shallowEntity.getPublishmentYear();
        int amount = shallowEntity.getAmount();
        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }
}
