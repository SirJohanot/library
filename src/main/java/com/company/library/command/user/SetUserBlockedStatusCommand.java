package com.company.library.command.user;

import com.company.library.constant.ParameterNameConstants;
import com.company.library.command.Command;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.CommandInvocationConstants;
import com.company.library.exception.ServiceException;
import com.company.library.service.UserService;

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
