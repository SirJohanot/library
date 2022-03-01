package com.epam.library.command.book;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBookCommand implements Command {

    private final BookService bookService;

    public DeleteBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);
        bookService.deleteBookById(bookId);
        return CommandResult.redirect(CommandInvocationConstants.BOOKS_PAGE);
    }
}
