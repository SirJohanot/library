package com.epam.library.command.repository;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.entity.BookOrder;

public interface BookOrderRepository extends Repository<BookOrder>, BookOrderDao {
}
