package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageChangeCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String newLocale = req.getParameter(ParameterNameConstants.LOCALE);

        HttpSession session = req.getSession();
        session.setAttribute(AttributeNameConstants.LOCALE, newLocale);

        return CommandResult.redirect(CommandInvocationConstants.MAIN_PAGE);
    }
}
