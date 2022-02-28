package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderToReadingHallCommand implements Command {

    private final BookService bookService;
    private final BookOrderService bookOrderService;

    public OrderToReadingHallCommand(BookService bookService, BookOrderService bookOrderService) {
        this.bookService = bookService;
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String idLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Book targetBook = bookService.getBookById(idLine);
        req.setAttribute(AttributeNameConstants.BOOK, targetBook);
        BookOrder dummyOrder = bookOrderService.buildDummyOrder("0", "TO_READING_HALL");
        req.setAttribute(AttributeNameConstants.BOOK_ORDER, dummyOrder);
        return CommandResult.forward(PagePathConstants.PLACE_ORDER);
    }
}
