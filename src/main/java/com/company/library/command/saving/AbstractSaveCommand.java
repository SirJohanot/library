package com.company.library.command.saving;

import com.company.library.command.Command;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.exception.ServiceException;
import com.company.library.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSaveCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        try {
            saveUsingService(req);
        } catch (ValidationException e) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, e.getMessage());
            return getFailureResult(req);
        }
        return CommandResult.redirect(getSuccessRedirectPath(req));
    }

    protected abstract void saveUsingService(HttpServletRequest req) throws ValidationException, ServiceException;

    protected abstract CommandResult getFailureResult(HttpServletRequest request);

    protected abstract String getSuccessRedirectPath(HttpServletRequest request);
}
