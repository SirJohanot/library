package com.patiun.library.command.viewentities;

import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.User;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.service.BookOrderService;
import com.patiun.library.comparator.OrderLibrarianPriorityComparator;
import com.patiun.library.comparator.OrderReaderPriorityComparator;
import com.patiun.library.specification.BookOrderIsRelatedToUserId;
import com.patiun.library.specification.NoSpecification;

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
