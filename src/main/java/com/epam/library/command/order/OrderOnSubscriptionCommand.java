package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderOnSubscriptionCommand implements Command {

    private final BookService bookService;
    private final BookOrderService bookOrderService;

    public OrderOnSubscriptionCommand(BookService bookService, BookOrderService bookOrderService) {
        this.bookService = bookService;
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String daysLine = req.getParameter(ParameterNameConstants.DAYS);
        int days = Integer.parseInt(daysLine);
        BookOrder dummyOrder = bookOrderService.buildPreviewOrder(days, RentalType.OUT_OF_LIBRARY);
        req.setAttribute(AttributeNameConstants.BOOK_ORDER, dummyOrder);
        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);
        Book targetBook = bookService.getBookById(bookId);
        req.setAttribute(AttributeNameConstants.BOOK, targetBook);
        return CommandResult.forward(PagePathConstants.PLACE_ORDER);
    }
}
