package com.patiun.library.command.saving;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.service.BookService;

import javax.servlet.http.HttpServletRequest;

public class EditBookCommand extends AbstractSaveBookCommand {

    public EditBookCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    protected CommandResult getFailureResult(HttpServletRequest request) {
        String bookIdLine = request.getParameter(ParameterNameConstants.BOOK_ID);

        return CommandResult.redirect(CommandInvocationConstants.EDIT_BOOK_PAGE + "&" + ParameterNameConstants.BOOK_ID + "=" + bookIdLine);
    }

    @Override
    protected String getSuccessRedirectPath(HttpServletRequest request) {
        return CommandInvocationConstants.BOOKS_PAGE;
    }
}
