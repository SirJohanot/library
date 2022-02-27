package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserPageCommand implements Command {

    private final UserService userService;

    public ViewUserPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long id = Long.parseLong(req.getParameter(ParameterNameConstants.USER_ID));
        User user = userService.getUserById(id);
        req.setAttribute(AttributeNameConstants.USER, user);
        return CommandResult.forward(PagePathConstants.VIEW_USER);
    }
}
