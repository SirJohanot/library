package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBookPageCommand implements Command {

    private final BookService bookService;

    public EditBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long bookId = Long.parseLong(req.getParameter(ParameterNameConstants.BOOK_ID));
        Book book = bookService.getBookById(bookId);
        req.setAttribute(AttributeNameConstants.BOOK, book);
        return CommandResult.forward(PagePathConstants.EDIT_BOOK);
    }
}
