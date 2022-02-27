package com.epam.library.dao;

import com.epam.library.entity.book.Publisher;

public interface PublisherDao extends Dao<Publisher>, SecondaryTableDao<Publisher>, NamedEntityDao<Publisher> {

}
