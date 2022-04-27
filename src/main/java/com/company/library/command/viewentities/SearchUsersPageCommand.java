package com.company.library.command.viewentities;

import com.company.library.constant.ParameterNameConstants;
import com.company.library.entity.User;
import com.company.library.specification.UserContainsLineSpecification;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import com.company.library.exception.ServiceException;
import com.company.library.pagination.Paginator;
import com.company.library.service.UserService;
import com.company.library.specification.Specification;

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

        return userService.getSpecifiedUsers(userSearchSpecification);
    }
}
