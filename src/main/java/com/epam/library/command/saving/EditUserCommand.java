package com.epam.library.command.saving;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.service.UserService;
import com.epam.library.validation.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class EditUserCommand extends AbstractSaveCommand {

    private final UserService userService;

    public EditUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void saveUsingService(HttpServletRequest req) throws ValidationException, ServiceException {
        String targetUserIdLine = req.getParameter(ParameterNameConstants.USER_ID);
        Long targetUserId = targetUserIdLine == null ? null : Long.valueOf(targetUserIdLine);

        String targetUserLogin = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String targetUserName = req.getParameter(ParameterNameConstants.USER_NAME);
        String targetUserSurname = req.getParameter(ParameterNameConstants.USER_SURNAME);

        String targetUserRoleLine = req.getParameter(ParameterNameConstants.USER_ROLE).toUpperCase();
        UserRole targetUserRole = UserRole.valueOf(targetUserRoleLine);

        String targetUserBlockedLine = req.getParameter(ParameterNameConstants.USER_BLOCKED);
        boolean targetUserBlocked = Boolean.parseBoolean(targetUserBlockedLine);

        userService.editUser(targetUserId, targetUserLogin, targetUserName, targetUserSurname, targetUserRole, targetUserBlocked, new UserValidator());
    }

    @Override
    protected CommandResult getFailureResult(HttpServletRequest request) {
        String targetUserIdLine = request.getParameter(ParameterNameConstants.USER_ID);

        return CommandResult.redirect(CommandInvocationConstants.EDIT_USER_PAGE + "&" + ParameterNameConstants.USER_ID + "=" + targetUserIdLine);
    }

    @Override
    protected String getSuccessRedirectPath(HttpServletRequest request) {
        return CommandInvocationConstants.USERS_PAGE;
    }

}
