package com.epam.library.command.saving;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.service.BookOrderService;
import com.epam.library.validation.BookOrderValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class SaveOrderCommand extends AbstractSaveCommand {

    private final BookOrderService bookOrderService;

    public SaveOrderCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    protected void saveUsingService(HttpServletRequest req) throws ValidationException, ServiceException {
        User orderingUser = (User) req.getSession().getAttribute(AttributeNameConstants.USER);
        Long userId = orderingUser.getId();

        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = Long.valueOf(bookIdLine);

        String startDateLine = req.getParameter(ParameterNameConstants.ORDER_START_DATE);
        Date startDate = Date.valueOf(startDateLine);

        String endDateLine = req.getParameter(ParameterNameConstants.ORDER_END_DATE);
        Date endDate = Date.valueOf(endDateLine);

        String rentalTypeLine = req.getParameter(ParameterNameConstants.ORDER_RENTAL_TYPE);
        RentalType rentalType = RentalType.valueOf(rentalTypeLine);
        bookOrderService.placeOrder(startDate, endDate, rentalType, bookId, userId, new BookOrderValidator());
    }

    @Override
    protected CommandResult getFailureResult(HttpServletRequest request) {
        return CommandResult.forward(PagePathConstants.ERROR);
    }

    @Override
    protected String getSuccessRedirectPath(HttpServletRequest request) {
        return CommandInvocationConstants.BOOKS_PAGE;
    }
}
