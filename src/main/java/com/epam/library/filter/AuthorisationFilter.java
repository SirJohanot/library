package com.epam.library.filter;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.CommandInvocationConstants;
import com.epam.library.constant.CommandLineConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AuthorisationFilter implements Filter {

    private final Map<UserRole, Set<String>> commandAccessMap = new HashMap<>();

    private final Set<String> notSignedInCommands = Set.of(CommandLineConstants.SIGN_IN_PAGE,
            CommandLineConstants.SIGN_IN,
            CommandLineConstants.SIGN_UP_PAGE,
            CommandLineConstants.SIGN_UP,
            CommandLineConstants.LANGUAGE_CHANGE);

    private final Set<String> readerCommands = Set.of(CommandLineConstants.SIGN_IN_PAGE,
            CommandLineConstants.SIGN_IN,
            CommandLineConstants.SIGN_UP_PAGE,
            CommandLineConstants.SIGN_UP,
            CommandLineConstants.LANGUAGE_CHANGE,
            CommandLineConstants.SIGN_OUT,
            CommandLineConstants.MAIN_PAGE,
            CommandLineConstants.BOOKS_PAGE,
            CommandLineConstants.SEARCH_BOOKS,
            CommandLineConstants.VIEW_BOOK_PAGE,
            CommandLineConstants.ORDERS_PAGE,
            CommandLineConstants.ADVANCE_ORDER,
            CommandLineConstants.VIEW_ORDER,
            CommandLineConstants.PLACE_ORDER);

    private final Set<String> librarianCommands = Set.of(CommandLineConstants.SIGN_IN_PAGE,
            CommandLineConstants.SIGN_IN,
            CommandLineConstants.SIGN_UP_PAGE,
            CommandLineConstants.SIGN_UP,
            CommandLineConstants.LANGUAGE_CHANGE,
            CommandLineConstants.SIGN_OUT,
            CommandLineConstants.MAIN_PAGE,
            CommandLineConstants.BOOKS_PAGE,
            CommandLineConstants.SEARCH_BOOKS,
            CommandLineConstants.VIEW_BOOK_PAGE,
            CommandLineConstants.ORDERS_PAGE,
            CommandLineConstants.ADVANCE_ORDER,
            CommandLineConstants.VIEW_ORDER);

    private final Set<String> adminCommands = Set.of(CommandLineConstants.SIGN_IN_PAGE,
            CommandLineConstants.SIGN_IN,
            CommandLineConstants.SIGN_UP_PAGE,
            CommandLineConstants.SIGN_UP,
            CommandLineConstants.LANGUAGE_CHANGE,
            CommandLineConstants.SIGN_OUT,
            CommandLineConstants.MAIN_PAGE,
            CommandLineConstants.BOOKS_PAGE,
            CommandLineConstants.SEARCH_BOOKS,
            CommandLineConstants.VIEW_BOOK_PAGE,
            CommandLineConstants.EDIT_BOOK_PAGE,
            CommandLineConstants.DELETE_BOOK,
            CommandLineConstants.ADD_A_BOOK_PAGE,
            CommandLineConstants.ADD_BOOK,
            CommandLineConstants.USERS_PAGE,
            CommandLineConstants.SEARCH_USERS,
            CommandLineConstants.VIEW_USER_PAGE,
            CommandLineConstants.BLOCK_USER,
            CommandLineConstants.UNBLOCK_USER,
            CommandLineConstants.EDIT_USER_PAGE,
            CommandLineConstants.EDIT_USER);

    @Override
    public void init(FilterConfig filterConfig) {
        commandAccessMap.put(null, notSignedInCommands);
        commandAccessMap.put(UserRole.READER, readerCommands);
        commandAccessMap.put(UserRole.LIBRARIAN, librarianCommands);
        commandAccessMap.put(UserRole.ADMIN, adminCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(AttributeNameConstants.USER);

        String command = httpServletRequest.getParameter(ParameterNameConstants.COMMAND);


        UserRole userRole = user == null ? null : user.getRole();
        Set<String> allowedCommandsForRole = commandAccessMap.get(userRole);
        if (!allowedCommandsForRole.contains(command)) {
            if (userRole != null) {
                httpServletResponse.sendRedirect(CommandInvocationConstants.MAIN_PAGE);
            } else {
                httpServletResponse.sendRedirect(CommandInvocationConstants.SIGN_IN_PAGE);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
