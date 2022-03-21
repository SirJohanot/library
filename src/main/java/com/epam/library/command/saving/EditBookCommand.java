package com.epam.library.command.saving;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.CommandLineConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.service.BookService;

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
        return CommandLineConstants.BOOKS_PAGE;
    }
}
