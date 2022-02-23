package com.epam.library.command;

import com.epam.library.command.result.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageChangeCommand implements Command {

    private static final String LOCALE_PARAMETER_AND_ATTRIBUTE_NAME = "locale";
    private static final String MAIN_PAGE_COMMAND_INVOCATION = "controller?command=mainPage";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String newLocale = req.getParameter(LOCALE_PARAMETER_AND_ATTRIBUTE_NAME);
        HttpSession session = req.getSession();
        session.setAttribute(LOCALE_PARAMETER_AND_ATTRIBUTE_NAME, newLocale);
        return CommandResult.redirect(MAIN_PAGE_COMMAND_INVOCATION);
    }
}
