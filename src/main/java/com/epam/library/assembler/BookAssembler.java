package com.epam.library.assembler;

import com.epam.library.dao.book.BookDao;
import com.epam.library.entity.book.Book;

/**
 * Uses BookDao, AuthorDao, GenreDao and PublisherDao to manipulate Books in the database
 */
public interface BookAssembler extends Assembler<Book>, BookDao {
}
