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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManualRequestInputFilter implements Filter {

    private final Map<String, Set<UserRole>> commandAccessMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {

        Set<UserRole> adminOnly = new HashSet<>();
        adminOnly.add(UserRole.ADMIN);

        Set<UserRole> readerOnly = new HashSet<>();
        readerOnly.add(UserRole.READER);

        Set<UserRole> librarianOnly = new HashSet<>();
        readerOnly.add(UserRole.LIBRARIAN);

        Set<UserRole> readerAndLibrarianOnly = new HashSet<>();
        readerAndLibrarianOnly.add(UserRole.READER);
        readerAndLibrarianOnly.add(UserRole.LIBRARIAN);

        Set<UserRole> everyone = new HashSet<>();
        everyone.add(UserRole.READER);
        everyone.add(UserRole.LIBRARIAN);
        everyone.add(UserRole.ADMIN);

        commandAccessMap.put(CommandLineConstants.SIGN_IN_PAGE, everyone);
        commandAccessMap.put(CommandLineConstants.LANGUAGE_CHANGE, everyone);
        commandAccessMap.put(CommandLineConstants.SIGN_IN, everyone);
        commandAccessMap.put(CommandLineConstants.SIGN_OUT, everyone);
        commandAccessMap.put(CommandLineConstants.MAIN_PAGE, everyone);
        commandAccessMap.put(CommandLineConstants.BOOKS_PAGE, everyone);
        commandAccessMap.put(CommandLineConstants.VIEW_BOOK_PAGE, everyone);

        commandAccessMap.put(CommandLineConstants.ORDERS_PAGE, readerAndLibrarianOnly);
        commandAccessMap.put(CommandLineConstants.VIEW_ORDER, readerAndLibrarianOnly);

        commandAccessMap.put(CommandLineConstants.ORDER_ON_SUBSCRIPTION, readerOnly);
        commandAccessMap.put(CommandLineConstants.ORDER_TO_READING_HALL, readerOnly);
        commandAccessMap.put(CommandLineConstants.ORDER, readerOnly);
        commandAccessMap.put(CommandLineConstants.COLLECT_ORDER, readerOnly);
        commandAccessMap.put(CommandLineConstants.RETURN_ORDER, readerOnly);
        commandAccessMap.put(CommandLineConstants.USER_ORDERS_PAGE, readerOnly);

        commandAccessMap.put(CommandLineConstants.GLOBAL_ORDERS_PAGE, librarianOnly);
        commandAccessMap.put(CommandLineConstants.DECLINE_ORDER, librarianOnly);
        commandAccessMap.put(CommandLineConstants.APPROVE_ORDER, librarianOnly);

        commandAccessMap.put(CommandLineConstants.EDIT_BOOK_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.DELETE_BOOK, adminOnly);
        commandAccessMap.put(CommandLineConstants.ADD_A_BOOK_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.SAVE_BOOK, adminOnly);
        commandAccessMap.put(CommandLineConstants.USERS_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.VIEW_USER_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.BLOCK_USER, adminOnly);
        commandAccessMap.put(CommandLineConstants.UNBLOCK_USER, adminOnly);
        commandAccessMap.put(CommandLineConstants.EDIT_USER_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.SAVE_USER, adminOnly);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(AttributeNameConstants.USER);
        String command = httpServletRequest.getParameter(ParameterNameConstants.COMMAND);
        if (user == null && !command.equals(CommandLineConstants.SIGN_IN_PAGE) && !command.equals(CommandLineConstants.SIGN_IN) && !command.equals(CommandLineConstants.LANGUAGE_CHANGE) || commandAccessMap.get(command) == null) {
            httpServletResponse.sendRedirect(CommandInvocationConstants.SIGN_IN_PAGE);
        } else {
            UserRole role = user.getRole();
            Set<UserRole> acceptedRolesForCommand = commandAccessMap.get(command);
            if (!acceptedRolesForCommand.contains(role)) {
                httpServletResponse.sendRedirect(CommandInvocationConstants.MAIN_PAGE);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
