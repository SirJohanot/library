package com.epam.library.command.saving;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSaveCommand implements Command {

    private final String successRedirectPath;
    private final String failureForwardPath;

    public AbstractSaveCommand(String successRedirectPath, String failureForwardPath) {
        this.successRedirectPath = successRedirectPath;
        this.failureForwardPath = failureForwardPath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        try {
            saveWithService(req);
        } catch (ValidationException e) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, e.getMessage());
            return CommandResult.forward(failureForwardPath);
        }
        return CommandResult.redirect(successRedirectPath);
    }

    protected abstract void saveWithService(HttpServletRequest req) throws ValidationException, ServiceException;
}
