package com.company.library.command.user;

import com.company.library.command.Command;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.CommandInvocationConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();

        return CommandResult.redirect(CommandInvocationConstants.SIGN_IN_PAGE);
    }
}
