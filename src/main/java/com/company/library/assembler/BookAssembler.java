package com.company.library.assembler;

import com.company.library.dao.book.BookDao;
import com.company.library.entity.book.Book;

/**
 * Uses BookDao, AuthorDao, GenreDao and PublisherDao to manipulate Books in the database
 */
public interface BookAssembler extends Assembler<Book>, BookDao {
}
