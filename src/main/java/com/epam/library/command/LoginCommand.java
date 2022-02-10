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
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Optional<User> user = userService.login(login, password);
            req.getSession().setAttribute("user", user.get().getLogin());
            return "WEB-INF/view/main.jsp";
        } catch (ServiceException e) {
            req.setAttribute("errorMessage", e.getMessage());
            return "index.jsp";
        }
    }
}
