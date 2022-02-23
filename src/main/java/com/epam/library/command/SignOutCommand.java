package com.epam.library.command;

import com.epam.library.command.result.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOutCommand implements Command {

    private static final String COMMAND_TO_FORWARD_TO_THE_SIGN_IN_PAGE = "controller?command=signInPage";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();
        return CommandResult.redirect(COMMAND_TO_FORWARD_TO_THE_SIGN_IN_PAGE);
    }
}
