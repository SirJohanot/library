package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BooksPageCommand implements Command {

    private final BookService bookService;

    public BooksPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<Book> bookList = bookService.getBooks();
        req.setAttribute(AttributeNameConstants.BOOK_LIST, bookList);
        return CommandResult.forward(PagePathConstants.BOOKS);
    }
}
