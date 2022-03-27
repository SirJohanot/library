package com.epam.library.command.order;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewOrderPageCommand implements Command {

    private final BookOrderService bookOrderService;

    public ViewOrderPageCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String orderIdLine = req.getParameter(ParameterNameConstants.ORDER_ID);
        Long orderId = Long.parseLong(orderIdLine);
        BookOrder order = bookOrderService.getOrderById(orderId);

        User userOfOrder = order.getUser();
        Long userOfOrderId = userOfOrder.getId();

        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(AttributeNameConstants.USER);
        UserRole currentUserRole = currentUser.getRole();

        if (currentUserRole == UserRole.READER) {
            Long currentUserId = currentUser.getId();
            if (!userOfOrderId.equals(currentUserId)) {
                throw new ServiceException("This order belongs to another User");
            }
        }

        req.setAttribute(AttributeNameConstants.BOOK_ORDER, order);

        return CommandResult.forward(PagePathConstants.VIEW_ORDER);
    }
}
