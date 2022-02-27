package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBookCommand implements Command {

    private final BookService bookService;

    public EditBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long bookId = Long.parseLong(req.getParameter(ParameterNameConstants.BOOK_ID));
        String title = req.getParameter(ParameterNameConstants.BOOK_TITLE);
        String authors = req.getParameter(ParameterNameConstants.BOOK_AUTHORS);
        String genre = req.getParameter(ParameterNameConstants.BOOK_GENRE);
        String publisher = req.getParameter(ParameterNameConstants.BOOK_PUBLISHER);
        String publishmentYear = req.getParameter(ParameterNameConstants.BOOK_PUBLISHMENT_YEAR);
        int amount = Integer.parseInt(req.getParameter(ParameterNameConstants.BOOK_AMOUNT));
        bookService.saveBook(bookId, title, authors, genre, publisher, publishmentYear, amount);
        return CommandResult.redirect(CommandInvocationConstants.BOOKS_PAGE);
    }
}
