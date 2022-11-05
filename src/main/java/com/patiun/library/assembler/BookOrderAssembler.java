package com.patiun.library.assembler;

import com.patiun.library.dao.BookOrderDao;
import com.patiun.library.entity.BookOrder;

/**
 * Uses BookRepository, UserDao and BookOrderDao to manipulate BookOrders in the database
 */
public interface BookOrderAssembler extends Assembler<BookOrder>, BookOrderDao {
}
