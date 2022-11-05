package com.patiun.library.command.viewentities;

import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.specification.BookContainsLineSpecification;
import com.patiun.library.specification.Specification;
import com.patiun.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchBooksPageCommand extends AbstractViewPageCommand<Book> {

    private static final int BOOKS_PER_PAGE = 5;

    private final BookService bookService;

    public SearchBooksPageCommand(BookService bookService, Paginator<Book> paginator) {
        super(paginator, BOOKS_PER_PAGE, AttributeNameConstants.BOOK_LIST, PagePathConstants.SEARCH_BOOKS);
        this.bookService = bookService;
    }

    @Override
    protected List<Book> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        String searchValue = req.getParameter(ParameterNameConstants.SEARCH_VALUE);
        req.setAttribute(AttributeNameConstants.SEARCH_VALUE, searchValue);
        Specification<Book> bookSearchSpecification = new BookContainsLineSpecification(searchValue);

        return bookService.getSpecifiedBooks(bookSearchSpecification);
    }
}
