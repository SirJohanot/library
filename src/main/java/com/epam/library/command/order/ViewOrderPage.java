package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewOrderPage implements Command {

    private final BookOrderService bookOrderService;

    public ViewOrderPage(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String orderIdLine = req.getParameter(ParameterNameConstants.ORDER_ID);
        Long orderId = Long.parseLong(orderIdLine);
        BookOrder order = bookOrderService.getOrderById(orderId);
        req.setAttribute(AttributeNameConstants.BOOK_ORDER, order);
        return CommandResult.forward(PagePathConstants.VIEW_ORDER);
    }
}
