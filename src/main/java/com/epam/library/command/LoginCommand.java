package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.LibraryConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String INVALID_CREDENTIALS_MESSAGE_ATTRIBUTE_NAME = "invalidCredentials";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Incorrect username or password.";

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(LOGIN_PARAMETER_NAME);
        String password = req.getParameter(PASSWORD_PARAMETER_NAME);
        Optional<User> user = userService.login(login, password);
        if (!user.isPresent()) {
            req.setAttribute(INVALID_CREDENTIALS_MESSAGE_ATTRIBUTE_NAME, INVALID_CREDENTIALS_MESSAGE);
            return CommandResult.forward(LibraryConstants.SIGN_IN_PAGE_PATH);
        }
        req.getSession().setAttribute(LibraryConstants.USER_ATTRIBUTE_NAME, user.get());
        return CommandResult.forward(LibraryConstants.MAIN_PAGE_PATH);
    }
}
