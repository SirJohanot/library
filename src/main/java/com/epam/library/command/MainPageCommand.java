package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.LibraryConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session.getAttribute(LibraryConstants.USER_ATTRIBUTE_NAME) != null) {
            return CommandResult.forward(LibraryConstants.MAIN_PAGE_PATH);
        }
        return CommandResult.forward(LibraryConstants.SIGN_IN_PAGE_PATH);
    }
}
