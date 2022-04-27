package com.company.library.assembler;

import com.company.library.dao.BookOrderDao;
import com.company.library.entity.BookOrder;

/**
 * Uses BookRepository, UserDao and BookOrderDao to manipulate BookOrders in the database
 */
public interface BookOrderAssembler extends Assembler<BookOrder>, BookOrderDao {
}
