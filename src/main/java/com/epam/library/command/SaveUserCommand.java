package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserCommand implements Command {

    private final UserService userService;

    public SaveUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String targetUserId = req.getParameter(ParameterNameConstants.USER_ID);
        String targetUserLogin = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String targetUserName = req.getParameter(ParameterNameConstants.USER_NAME);
        String targetUserSurname = req.getParameter(ParameterNameConstants.USER_SURNAME);
        String targetUserRole = req.getParameter(ParameterNameConstants.USER_ROLE);
        String targetUserBlocked = req.getParameter(ParameterNameConstants.USER_BLOCKED);
        userService.saveUser(targetUserId, targetUserLogin, targetUserName, targetUserSurname, targetUserRole, targetUserBlocked);
        return CommandResult.redirect(CommandInvocationConstants.USERS_PAGE);
    }
}
