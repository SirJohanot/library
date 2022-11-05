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

public class EditBookPageCommand implements Command {

    private final BookService bookService;

    public EditBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);
        Book book = bookService.getBookById(bookId);

        req.setAttribute(AttributeNameConstants.BOOK, book);

        return CommandResult.forward(PagePathConstants.EDIT_BOOK);
    }
}
