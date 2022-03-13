package com.epam.library.command.repository;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.entity.BookOrder;

/**
 * Uses BookRepository, UserDao and BookOrderDao to manipulate BookOrders in the database
 */
public interface BookOrderRepository extends Repository<BookOrder>, BookOrderDao {
}
