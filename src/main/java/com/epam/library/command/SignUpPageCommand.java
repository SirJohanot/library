package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return CommandResult.forward(PagePathConstants.SIGN_UP);
    }
}
