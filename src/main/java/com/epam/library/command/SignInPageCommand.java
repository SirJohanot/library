package com.epam.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInPageCommand implements Command {

    private static final String SIGN_IN_PAGE_PATH = "/index.jsp";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return CommandResult.forward(SIGN_IN_PAGE_PATH);
    }
}
