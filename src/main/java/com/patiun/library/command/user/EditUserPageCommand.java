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
