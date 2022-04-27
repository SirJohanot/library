package com.company.library.command.viewentities;

import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.entity.BookOrder;
import com.company.library.entity.User;
import com.company.library.exception.ServiceException;
import com.company.library.pagination.Paginator;
import com.company.library.service.BookOrderService;
import com.company.library.comparator.OrderLibrarianPriorityComparator;
import com.company.library.comparator.OrderReaderPriorityComparator;
import com.company.library.specification.BookOrderIsRelatedToUserId;
import com.company.library.specification.NoSpecification;

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
