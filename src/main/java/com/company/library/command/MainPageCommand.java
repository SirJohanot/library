package com.company.library.command;

import com.company.library.command.result.CommandResult;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;

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
