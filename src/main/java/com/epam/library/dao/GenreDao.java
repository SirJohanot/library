package com.epam.library.dao;

import com.epam.library.entity.book.Genre;

public interface GenreDao extends Dao<Genre>, SecondaryTableDao<Genre>, NamedEntityDao<Genre> {

}
