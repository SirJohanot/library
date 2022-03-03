package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderStateAdvancementCommand implements Command {

    private final BookOrderService bookOrderService;
    private final RentalState newState;

    public OrderStateAdvancementCommand(BookOrderService bookOrderService, RentalState newState) {
        this.bookOrderService = bookOrderService;
        this.newState = newState;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String orderIdLine = req.getParameter(ParameterNameConstants.ORDER_ID);
        Long orderId = Long.valueOf(orderIdLine);
        bookOrderService.advanceOrderState(orderId, newState);
        return CommandResult.redirect(CommandInvocationConstants.ORDERS_PAGE);
    }
}
