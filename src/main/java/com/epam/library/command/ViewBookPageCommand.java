package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.LibraryConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewBookPageCommand implements Command {

    private static final String BOOK_ID_PARAMETER_NAME = "bookId";
    private static final String BOOK_ATTRIBUTE_NAME = "book";

    private final BookService bookService;

    public ViewBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.parseLong(req.getParameter(BOOK_ID_PARAMETER_NAME));
        Book requestedBook = bookService.getBookById(id);
        req.setAttribute(BOOK_ATTRIBUTE_NAME, requestedBook);
        return CommandResult.forward(LibraryConstants.VIEW_BOOK_PAGE_PATH);
    }
}
