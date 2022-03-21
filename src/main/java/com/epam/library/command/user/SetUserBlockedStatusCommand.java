package com.epam.library.command.user;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetUserBlockedStatusCommand implements Command {

    private final UserService userService;
    private final boolean newBlocked;

    public SetUserBlockedStatusCommand(UserService userService, boolean newBlocked) {
        this.userService = userService;
        this.newBlocked = newBlocked;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String targetUserIdLine = req.getParameter(ParameterNameConstants.USER_ID);
        Long targetUserId = Long.valueOf(targetUserIdLine);

        userService.setUserBlockStatus(targetUserId, newBlocked);

        return CommandResult.redirect(CommandInvocationConstants.USERS_PAGE);
    }
}
