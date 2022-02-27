package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.PagePathConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return CommandResult.forward(PagePathConstants.SIGN_IN);
    }
}
