package com.patiun.library.command.order;

import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.User;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.service.BookOrderService;

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
