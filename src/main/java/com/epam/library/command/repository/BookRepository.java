package com.epam.library.command.repository;

import com.epam.library.dao.book.BookDao;
import com.epam.library.entity.book.Book;

public interface BookRepository extends Repository<Book>, BookDao {
}
