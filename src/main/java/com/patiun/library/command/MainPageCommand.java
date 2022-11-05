package com.patiun.library.command;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session.getAttribute(AttributeNameConstants.USER) != null) {
            return CommandResult.forward(PagePathConstants.MAIN);
        }

        return CommandResult.forward(PagePathConstants.SIGN_IN);
    }
}
