package com.patiun.library.command.user;

import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.service.UserService;

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
