package com.company.library.command.viewentities;

import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.entity.book.Book;
import com.company.library.exception.ServiceException;
import com.company.library.pagination.Paginator;
import com.company.library.service.BookService;
import com.company.library.specification.NoSpecification;

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
