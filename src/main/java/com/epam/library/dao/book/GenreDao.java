package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.dao.NamedEntityDao;
import com.epam.library.dao.SecondaryTableDao;
import com.epam.library.entity.book.Genre;

public interface GenreDao extends Dao<Genre>, SecondaryTableDao<Genre>, NamedEntityDao<Genre> {

}
