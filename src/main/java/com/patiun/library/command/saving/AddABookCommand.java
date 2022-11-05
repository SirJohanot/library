package com.patiun.library.command.saving;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.service.BookService;

import javax.servlet.http.HttpServletRequest;

public class AddABookCommand extends AbstractSaveBookCommand {

    public AddABookCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    protected CommandResult getFailureResult(HttpServletRequest request) {
        return CommandResult.forward(PagePathConstants.ADD_A_BOOK);
    }

    @Override
    protected String getSuccessRedirectPath(HttpServletRequest request) {
        return CommandInvocationConstants.BOOKS_PAGE;
    }
}
