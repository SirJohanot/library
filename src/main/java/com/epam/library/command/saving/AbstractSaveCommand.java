package com.epam.library.command.saving;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;

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
