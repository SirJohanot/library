package com.epam.library.service;

import com.epam.library.dao.daohelper.DaoHelperFactory;
import com.epam.library.exception.ServiceException;
import org.junit.Test;

public class BookServiceImplTest {

    private final BookServiceImpl bookService = new BookServiceImpl(new DaoHelperFactory());

    @Test
    public void testEditBook() throws ServiceException {
        bookService.saveBook(1L, "War and Peace", "Leo Tolstoy, Pavel Tsivunchyk", "Historical novel", "Aversev", "2004", 4);
    }
}
