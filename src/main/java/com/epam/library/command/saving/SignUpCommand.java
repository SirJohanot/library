package com.epam.library.command.saving;

import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.service.UserService;
import com.epam.library.validation.PasswordValidator;
import com.epam.library.validation.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand extends AbstractSaveCommand {

    private final UserService userService;

    public SignUpCommand(String successRedirectPath, String failureForwardPath, UserService userService) {
        super(successRedirectPath, failureForwardPath);
        this.userService = userService;
    }

    @Override
    protected void saveWithService(HttpServletRequest req) throws ValidationException, ServiceException {
        String login = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String name = req.getParameter(ParameterNameConstants.USER_NAME);
        String surname = req.getParameter(ParameterNameConstants.USER_SURNAME);
        String password = req.getParameter(ParameterNameConstants.USER_PASSWORD);
        String confirmedPassword = req.getParameter(ParameterNameConstants.USER_CONFIRMED_PASSWORD);
        if (password == null || !password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords don't match");
        }

        userService.signUp(login, password, name, surname, new UserValidator(), new PasswordValidator());
    }
}
