package com.patiun.library.command.viewentities;

import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.service.BookService;
import com.patiun.library.specification.NoSpecification;

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
        return bookService.getSpecifiedBooks(new NoSpecification<>());
    }
}
