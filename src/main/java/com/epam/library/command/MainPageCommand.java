package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;

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
