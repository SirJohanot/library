package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.LibraryConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBookCommand implements Command {

    private static final String BOOKS_PAGE_COMMAND_INVOCATION = "controller?command=booksPage";

    private static final String BOOK_TITLE_PARAMETER_NAME = "title";
    private static final String BOOK_AUTHORS_PARAMETER_NAME = "authors";
    private static final String BOOK_GENRE_PARAMETER_NAME = "genre";
    private static final String BOOK_PUBLISHER_PARAMETER_NAME = "publisher";
    private static final String BOOK_PUBLISHMENT_YEAR_PARAMETER_NAME = "publishmentYear";
    private static final String BOOK_AMOUNT_PARAMETER_NAME = "amount";

    private final BookService bookService;

    public EditBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long bookId = Long.parseLong(req.getParameter(LibraryConstants.BOOK_ID_PARAMETER_NAME));
        String title = req.getParameter(BOOK_TITLE_PARAMETER_NAME);
        String authors = req.getParameter(BOOK_AUTHORS_PARAMETER_NAME);
        String genre = req.getParameter(BOOK_GENRE_PARAMETER_NAME);
        String publisher = req.getParameter(BOOK_PUBLISHER_PARAMETER_NAME);
        String publishmentYear = req.getParameter(BOOK_PUBLISHMENT_YEAR_PARAMETER_NAME);
        int amount = Integer.parseInt(req.getParameter(BOOK_AMOUNT_PARAMETER_NAME));
        bookService.editBook(bookId, title, authors, genre, publisher, publishmentYear, amount);
        return CommandResult.redirect(BOOKS_PAGE_COMMAND_INVOCATION);
    }
}
