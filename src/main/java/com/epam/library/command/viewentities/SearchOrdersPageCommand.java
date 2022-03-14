package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.BookOrderService;
import com.epam.library.service.comparator.OrderLibrarianPriorityComparator;
import com.epam.library.service.comparator.OrderReaderPriorityComparator;
import com.epam.library.specification.BookOrderContainsLineSpecification;
import com.epam.library.specification.BookOrderIsRelatedToUserId;
import com.epam.library.specification.Specification;

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
