package com.epam.library.command.order.statemanipulation;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReaderOrderActionCommand implements Command {

    private final BookOrderService bookOrderService;
    private final RentalState newState;

    public ReaderOrderActionCommand(BookOrderService bookOrderService, RentalState newState) {
        this.bookOrderService = bookOrderService;
        this.newState = newState;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(AttributeNameConstants.USER);
        if (currentUser.getRole() != UserRole.READER) {
            throw new ServiceException("Only readers can collect and return books");
        }
        String orderIdLine = req.getParameter(ParameterNameConstants.ORDER_ID);
        Long orderId = Long.valueOf(orderIdLine);
        bookOrderService.setOrderState(orderId, newState);
        return CommandResult.redirect(CommandInvocationConstants.ORDERS_PAGE);
    }
}
