package com.epam.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainPageCommand implements Command {

    private static final String USER_ATTRIBUTE_NAME = "user";
    private static final String MAIN_PAGE_PATH = "/WEB-INF/view/main.jsp";
    private static final String SIGN_IN_PAGE_PATH = "/index.jsp";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session.getAttribute(USER_ATTRIBUTE_NAME) != null) {
            return CommandResult.forward(MAIN_PAGE_PATH);
        }
        return CommandResult.forward(SIGN_IN_PAGE_PATH);
    }
}
