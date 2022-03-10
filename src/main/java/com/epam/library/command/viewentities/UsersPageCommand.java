package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UsersPageCommand extends AbstractViewPageCommand<User> {

    private static final int USERS_PER_PAGE = 5;

    private final UserService userService;

    public UsersPageCommand(UserService userService, Paginator<User> userPaginator) {
        super(userPaginator, USERS_PER_PAGE, AttributeNameConstants.USER_LIST, PagePathConstants.USERS);
        this.userService = userService;
    }

    @Override
    protected List<User> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        return userService.getAllUsers();
    }
}
