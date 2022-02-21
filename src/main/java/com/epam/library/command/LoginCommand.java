package com.epam.library.command;

import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> user = userService.login(login, password);
        if (!user.isPresent()) {
            req.setAttribute("errorMessage", "Incorrect username or password.");
            return CommandResult.forward("/index.jsp");
        }
        req.getSession().setAttribute("user", user);
        return CommandResult.forward("/WEB-INF/view/main.jsp");
    }
}
