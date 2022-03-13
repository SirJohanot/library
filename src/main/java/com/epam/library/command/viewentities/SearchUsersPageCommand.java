package com.epam.library.command.viewentities;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.UserService;
import com.epam.library.specification.Specification;
import com.epam.library.specification.UserContainsLineSpecification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchUsersPageCommand extends AbstractViewPageCommand<User> {

    private static final int USERS_PER_PAGE = 5;

    private final UserService userService;

    public SearchUsersPageCommand(UserService userService, Paginator<User> userPaginator) {
        super(userPaginator, USERS_PER_PAGE, AttributeNameConstants.USER_LIST, PagePathConstants.SEARCH_USERS);
        this.userService = userService;
    }

    @Override
    protected List<User> getEntitiesUsingService(HttpServletRequest req) throws ServiceException {
        String searchValue = req.getParameter(ParameterNameConstants.SEARCH_VALUE);
        req.setAttribute(AttributeNameConstants.SEARCH_VALUE, searchValue);
        Specification<User> userSearchSpecification = new UserContainsLineSpecification(searchValue);

        return userService.getAllSpecifiedUsers(userSearchSpecification);
    }
}
