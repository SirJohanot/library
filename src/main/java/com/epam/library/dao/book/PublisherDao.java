package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.dao.SecondaryTableDao;
import com.epam.library.entity.book.Publisher;

/**
 * This is an empty interface for now, but new methods may later be added if needed
 */

public interface PublisherDao extends Dao<Publisher>, SecondaryTableDao<Publisher> {

}
