package com.epam.library.command.user;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignInCommand implements Command {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Incorrect username or password.";
    private static final String BLOCKED_MESSAGE = "You are blocked from using this resource.";

    private final UserService userService;

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String password = req.getParameter(ParameterNameConstants.USER_PASSWORD);
        Optional<User> user = userService.signIn(login, password);
        if (user.isEmpty()) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, INVALID_CREDENTIALS_MESSAGE);
            return CommandResult.forward(PagePathConstants.SIGN_IN);
        }
        if (user.get().isBlocked()) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, BLOCKED_MESSAGE);
            return CommandResult.forward(PagePathConstants.SIGN_IN);
        }
        req.getSession().setAttribute(AttributeNameConstants.USER, user.get());
        return CommandResult.redirect(CommandInvocationConstants.MAIN_PAGE);
    }
}
