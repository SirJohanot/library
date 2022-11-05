package com.patiun.library.command.saving;

import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.CommandInvocationConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.exception.ValidationException;
import com.patiun.library.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand extends AbstractSaveCommand {

    private final UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void saveUsingService(HttpServletRequest req) throws ValidationException, ServiceException {
        String login = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String name = req.getParameter(ParameterNameConstants.USER_NAME);
        String surname = req.getParameter(ParameterNameConstants.USER_SURNAME);
        String password = req.getParameter(ParameterNameConstants.USER_PASSWORD);
        String confirmedPassword = req.getParameter(ParameterNameConstants.USER_CONFIRMED_PASSWORD);

        if (password == null || !password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords don't match");
        }

        userService.signUp(login, password, name, surname);
    }

    @Override
    protected CommandResult getFailureResult(HttpServletRequest request) {
        return CommandResult.forward(PagePathConstants.SIGN_UP);
    }

    @Override
    protected String getSuccessRedirectPath(HttpServletRequest request) {
        return CommandInvocationConstants.SIGN_IN_PAGE;
    }
}
