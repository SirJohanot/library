package com.epam.library.command.saving;

import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.service.UserService;
import com.epam.library.validation.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class EditUserCommand extends AbstractSaveCommand {

    private final UserService userService;

    public EditUserCommand(String successRedirectPath, String failureForwardPath, UserService userService) {
        super(successRedirectPath, failureForwardPath);
        this.userService = userService;
    }

    @Override
    protected void saveWithService(HttpServletRequest req) throws ValidationException, ServiceException {
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
}
