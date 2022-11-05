package com.patiun.library.command.saving;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.User;
import com.patiun.library.entity.enumeration.RentalType;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.exception.ValidationException;
import com.patiun.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;

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

        String daysLine = req.getParameter(ParameterNameConstants.DAYS);
        int days = Integer.parseInt(daysLine);

        String typeLine = req.getParameter(ParameterNameConstants.TYPE);
        RentalType type = RentalType.valueOf(typeLine);

        bookOrderService.placeOrder(days, type, bookId, userId);
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
