package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.BookOrderService;
import com.epam.library.service.comparator.OrderLibrarianPriorityComparator;
import com.epam.library.service.comparator.OrderReaderPriorityComparator;
import com.epam.library.specification.BookOrderIsRelatedToUserId;
import com.epam.library.specification.NoSpecification;

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
                Long userId = currentUser.getId();
                return orderService.getSpecifiedOrders(new BookOrderIsRelatedToUserId(userId), new OrderReaderPriorityComparator());
            case LIBRARIAN:
                return orderService.getSpecifiedOrders(new NoSpecification<>(), new OrderLibrarianPriorityComparator());
            default:
                return new ArrayList<>();
        }
    }
}
