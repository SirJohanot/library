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
