package com.patiun.library.command.order;

import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.User;
import com.patiun.library.entity.enumeration.UserRole;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.service.BookOrderService;

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
