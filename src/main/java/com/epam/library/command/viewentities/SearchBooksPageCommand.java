package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.BookService;
import com.epam.library.specification.BookContainsLineSpecification;
import com.epam.library.specification.Specification;

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
