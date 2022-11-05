package com.patiun.library.assembler;

import com.patiun.library.dao.book.BookDao;
import com.patiun.library.entity.book.Book;

/**
 * Uses BookDao, AuthorDao, GenreDao and PublisherDao to manipulate Books in the database
 */
public interface BookAssembler extends Assembler<Book>, BookDao {
}
