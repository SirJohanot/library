package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookOrderService;
import com.epam.library.util.Paginator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OrdersPageCommand extends AbstractViewPageCommand<BookOrder> {

    private static final int ORDERS_PER_PAGE = 5;

    private final BookOrderService orderService;

    public OrdersPageCommand(BookOrderService orderService, Paginator<BookOrder> bookOrderPaginator) {
        super(bookOrderPaginator, ORDERS_PER_PAGE, AttributeNameConstants.ORDER_LIST, PagePathConstants.ORDERS);
        this.orderService = orderService;
    }

    @Override
    protected List<BookOrder> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        User currentUser = (User) req.getSession().getAttribute(AttributeNameConstants.USER);
        switch (currentUser.getRole()) {
            case READER:
                return orderService.getReaderOrders(currentUser.getId());
            case LIBRARIAN:
                return orderService.getOrdersForLibrarian();
            default:
                return new ArrayList<>();
        }
    }
}
