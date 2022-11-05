package com.patiun.library.command.user;

import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.entity.User;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.service.UserService;

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
