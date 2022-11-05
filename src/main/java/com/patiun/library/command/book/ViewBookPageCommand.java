package com.patiun.library.command.book;

import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.service.BookService;

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
