package com.patiun.library.command.viewentities;

import com.patiun.library.entity.User;
import com.patiun.library.specification.NoSpecification;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.PagePathConstants;
import com.patiun.library.exception.ServiceException;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.service.UserService;

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
        return userService.getSpecifiedUsers(new NoSpecification<>());
    }
}
