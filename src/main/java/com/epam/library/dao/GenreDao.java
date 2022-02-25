package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.exception.DaoException;

import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getGenreOfBook(Book book) throws DaoException;
}
