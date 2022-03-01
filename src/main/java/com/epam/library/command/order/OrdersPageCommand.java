package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class OrdersPageCommand implements Command {

    private final BookOrderService orderService;

    public OrdersPageCommand(BookOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<BookOrder> orderList;
        User currentUser = (User) req.getSession().getAttribute(AttributeNameConstants.USER);
        switch (currentUser.getRole()) {
            case READER:
                orderList = orderService.getUserOrders(currentUser.getId());
                break;
            case LIBRARIAN:
                orderList = orderService.getAllOrders();
                break;
            default:
                orderList = new ArrayList<>();
        }
        req.setAttribute(AttributeNameConstants.ORDER_LIST, orderList);
        return CommandResult.forward(PagePathConstants.ORDERS);
    }
}
