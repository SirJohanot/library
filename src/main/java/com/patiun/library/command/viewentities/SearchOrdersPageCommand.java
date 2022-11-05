package com.patiun.library.command.viewentities;

import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.User;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.service.BookOrderService;
import com.patiun.library.comparator.OrderLibrarianPriorityComparator;
import com.patiun.library.comparator.OrderReaderPriorityComparator;
import com.patiun.library.specification.BookOrderContainsLineSpecification;
import com.patiun.library.specification.BookOrderIsRelatedToUserId;
import com.patiun.library.specification.Specification;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SearchOrdersPageCommand extends AbstractViewPageCommand<BookOrder> {

    private static final int ORDERS_PER_PAGE = 5;

    private final BookOrderService bookOrderService;

    public SearchOrdersPageCommand(BookOrderService bookOrderService, Paginator<BookOrder> bookOrderPaginator) {
        super(bookOrderPaginator, ORDERS_PER_PAGE, AttributeNameConstants.ORDER_LIST, PagePathConstants.SEARCH_ORDERS);
        this.bookOrderService = bookOrderService;
    }

    @Override
    protected List<BookOrder> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        String searchValue = req.getParameter(ParameterNameConstants.SEARCH_VALUE);
        req.setAttribute(AttributeNameConstants.SEARCH_VALUE, searchValue);
        Specification<BookOrder> bookOrderSearchSpecification = new BookOrderContainsLineSpecification(searchValue);
        User currentUser = (User) req.getSession().getAttribute(AttributeNameConstants.USER);
        switch (currentUser.getRole()) {
            case READER:
                Long userId = currentUser.getId();
                Specification<BookOrder> bookOrderIsRelatedToUserSpecification = new BookOrderIsRelatedToUserId(bookOrderSearchSpecification, userId);
                return bookOrderService.getSpecifiedOrders(bookOrderIsRelatedToUserSpecification, new OrderReaderPriorityComparator());
            case LIBRARIAN:
                return bookOrderService.getSpecifiedOrders(bookOrderSearchSpecification, new OrderLibrarianPriorityComparator());
            default:
                return new ArrayList<>();
        }
    }
}
