package com.company.library.command.book;

import com.company.library.command.Command;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.constant.ParameterNameConstants;
import com.company.library.entity.book.Book;
import com.company.library.exception.ServiceException;
import com.company.library.service.BookService;

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
