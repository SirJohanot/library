package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsersPageCommand implements Command {

    private final UserService userService;

    public UsersPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<User> userList = userService.getAllUsers();
        req.setAttribute(AttributeNameConstants.USER_LIST, userList);
        return CommandResult.forward(PagePathConstants.USERS);
    }
}
