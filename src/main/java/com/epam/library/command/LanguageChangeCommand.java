package com.epam.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageChangeCommand implements Command {

    private static final String LOCALE_PARAMETER_AND_ATTRIBUTE_NAME = "locale";
    private static final String PAGE_TO_FORWARD_TO_PATH = "/index.jsp";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String newLocale = req.getParameter(LOCALE_PARAMETER_AND_ATTRIBUTE_NAME);
        HttpSession session = req.getSession();
        session.setAttribute(LOCALE_PARAMETER_AND_ATTRIBUTE_NAME, newLocale);
        return CommandResult.forward(PAGE_TO_FORWARD_TO_PATH);    //I need to somehow make this command tell the Controller to forward the req, resp to the same .jsp file
    }
}
