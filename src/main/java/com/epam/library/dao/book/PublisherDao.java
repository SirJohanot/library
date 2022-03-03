package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.dao.SecondaryTableDao;
import com.epam.library.entity.book.Publisher;

public interface PublisherDao extends Dao<Publisher>, SecondaryTableDao<Publisher> {

}
