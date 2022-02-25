package com.epam.library.dao;

import com.epam.library.entity.book.Genre;
import com.epam.library.exception.DaoException;

import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getGenre(Long genreId) throws DaoException;

    Optional<Genre> getGenreByName(String name) throws DaoException;

    void saveGenre(Genre genre) throws DaoException;
}
