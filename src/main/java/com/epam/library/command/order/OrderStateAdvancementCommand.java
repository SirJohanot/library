package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderStateAdvancementCommand implements Command {

    private final BookOrderService bookOrderService;

    public OrderStateAdvancementCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(AttributeNameConstants.USER);
        Long currentUserId = currentUser.getId();

        String orderIdLine = req.getParameter(ParameterNameConstants.ORDER_ID);
        Long orderId = Long.valueOf(orderIdLine);

        String stateLine = req.getParameter(ParameterNameConstants.STATE);
        RentalState state = RentalState.valueOf(stateLine);

        bookOrderService.advanceOrderState(orderId, currentUserId, state);

        return CommandResult.redirect(CommandInvocationConstants.ORDERS_PAGE);
    }
}
