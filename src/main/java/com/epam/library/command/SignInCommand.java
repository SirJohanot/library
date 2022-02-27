package com.epam.library.command;

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

    private final UserService userService;

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(ParameterNameConstants.LOGIN);
        String password = req.getParameter(ParameterNameConstants.PASSWORD);
        Optional<User> user = userService.signIn(login, password);
        if (user.isEmpty()) {
            req.setAttribute(AttributeNameConstants.INVALID_CREDENTIALS_MESSAGE, INVALID_CREDENTIALS_MESSAGE);
            return CommandResult.forward(PagePathConstants.SIGN_IN);
        }
        req.getSession().setAttribute(AttributeNameConstants.USER, user.get());
        return CommandResult.redirect(CommandInvocationConstants.MAIN_PAGE);
    }
}
