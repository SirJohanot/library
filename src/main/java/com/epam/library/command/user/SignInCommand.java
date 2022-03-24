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
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInCommand implements Command {

    private final UserService userService;

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(ParameterNameConstants.USER_LOGIN);
        String password = req.getParameter(ParameterNameConstants.USER_PASSWORD);

        Optional<User> userOptional = userService.signIn(login, password);
        if (userOptional.isEmpty()) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, "Incorrect username or password.");
            return CommandResult.forward(PagePathConstants.SIGN_IN);
        }
        if (userOptional.get().isBlocked()) {
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, "You are blocked from using this resource.");
            return CommandResult.forward(PagePathConstants.SIGN_IN);
        }
        User user = userOptional.get();

        HttpSession session = req.getSession();
        session.setAttribute(AttributeNameConstants.USER, user);

        return CommandResult.redirect(CommandInvocationConstants.MAIN_PAGE);
    }
}
