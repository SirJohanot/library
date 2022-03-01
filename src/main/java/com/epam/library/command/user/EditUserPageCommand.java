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

public class EditUserPageCommand implements Command {

    private final UserService userService;

    public EditUserPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String targetUserIdLine = req.getParameter(ParameterNameConstants.USER_ID);
        Long targetUserId = Long.valueOf(targetUserIdLine);
        User targetUser = userService.getUserById(targetUserId);
        req.setAttribute(AttributeNameConstants.TARGET_USER, targetUser);
        return CommandResult.forward(PagePathConstants.EDIT_USER);
    }
}
