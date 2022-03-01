package com.epam.library.command.book;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Year;

public class SaveBookCommand implements Command {

    private final BookService bookService;

    public SaveBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);
        String title = req.getParameter(ParameterNameConstants.BOOK_TITLE);
        String authors = req.getParameter(ParameterNameConstants.BOOK_AUTHORS);
        String genre = req.getParameter(ParameterNameConstants.BOOK_GENRE);
        String publisher = req.getParameter(ParameterNameConstants.BOOK_PUBLISHER);
        String publishmentYearLine = req.getParameter(ParameterNameConstants.BOOK_PUBLISHMENT_YEAR);
        Year publishmentYear = Year.parse(publishmentYearLine);
        String amountLine = req.getParameter(ParameterNameConstants.BOOK_AMOUNT);
        Integer amount = Integer.valueOf(amountLine);
        bookService.saveBook(bookId, title, authors, genre, publisher, publishmentYear, amount);
        return CommandResult.redirect(CommandInvocationConstants.BOOKS_PAGE);
    }
}
