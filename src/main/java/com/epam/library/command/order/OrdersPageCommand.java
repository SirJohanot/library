package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersPageCommand implements Command {

    private final BookOrderService orderService;

    public OrdersPageCommand(BookOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        //TODO: get orders from db
        return CommandResult.forward(PagePathConstants.ORDERS);
    }
}
