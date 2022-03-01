package com.epam.library.command.book;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewBookPageCommand implements Command {

    private final BookService bookService;

    public ViewBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String idLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long id = Long.valueOf(idLine);
        Book requestedBook = bookService.getBookById(id);
        req.setAttribute(AttributeNameConstants.BOOK, requestedBook);
        return CommandResult.forward(PagePathConstants.VIEW_BOOK);
    }
}
