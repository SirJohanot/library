package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class OrderCommand implements Command {

    private final BookOrderService bookOrderService;

    public OrderCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        User orderingUser = (User) req.getSession().getAttribute(AttributeNameConstants.USER);
        Long userId = orderingUser.getId();

        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);

        String startDateLine = req.getParameter(ParameterNameConstants.START_DATE);
        Date startDate = Date.valueOf(startDateLine);

        String endDateLine = req.getParameter(ParameterNameConstants.END_DATE);
        Date endDate = Date.valueOf(endDateLine);

        String rentalTypeLine = req.getParameter(ParameterNameConstants.RENTAL_TYPE);
        RentalType rentalType = RentalType.valueOf(rentalTypeLine);
        
        bookOrderService.placeOrder(startDate, endDate, rentalType, bookId, userId);
        return CommandResult.redirect(CommandInvocationConstants.ORDERS_PAGE);
    }
}
