package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BooksPageCommand extends AbstractViewPageCommand<Book> {

    private static final int BOOKS_PER_PAGE = 5;

    private final BookService bookService;

    public BooksPageCommand(BookService bookService, Paginator<Book> bookPaginator) {
        super(bookPaginator, BOOKS_PER_PAGE, AttributeNameConstants.BOOK_LIST, PagePathConstants.BOOKS);
        this.bookService = bookService;
    }

    @Override
    protected List<Book> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        return bookService.getAllBooks();
    }
}
