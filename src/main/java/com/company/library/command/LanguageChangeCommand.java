package com.company.library.command;

import com.company.library.command.result.CommandResult;
import com.company.library.constant.ParameterNameConstants;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.CommandInvocationConstants;

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
