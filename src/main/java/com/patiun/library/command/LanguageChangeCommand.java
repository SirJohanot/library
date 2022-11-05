package com.patiun.library.command;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.CommandInvocationConstants;

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
