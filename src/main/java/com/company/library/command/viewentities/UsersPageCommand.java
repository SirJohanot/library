package com.company.library.command.viewentities;

import com.company.library.entity.User;
import com.company.library.specification.NoSpecification;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.exception.ServiceException;
import com.company.library.pagination.Paginator;
import com.company.library.service.UserService;

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
