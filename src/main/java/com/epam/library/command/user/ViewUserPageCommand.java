package com.epam.library.command.user;

import com.epam.library.command.Command;
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
        String idLine = req.getParameter(ParameterNameConstants.USER_ID);
        Long id = Long.valueOf(idLine);

        User user = userService.getUserById(id);

        req.setAttribute(AttributeNameConstants.TARGET_USER, user);
        
        return CommandResult.forward(PagePathConstants.VIEW_USER);
    }
}
