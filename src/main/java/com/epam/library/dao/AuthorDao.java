package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.exception.DaoException;

import java.util.List;

public interface AuthorDao {

    List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException;
}
