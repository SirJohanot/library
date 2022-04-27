package com.company.library.command.user;

import com.company.library.command.Command;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.ParameterNameConstants;
import com.company.library.entity.User;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.exception.ServiceException;
import com.company.library.service.UserService;

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
