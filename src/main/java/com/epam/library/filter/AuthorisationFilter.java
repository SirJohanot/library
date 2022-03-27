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

public class AuthorisationFilter implements Filter {

    private final Map<String, Set<UserRole>> commandAccessMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {

        Set<UserRole> anyone = new HashSet<>();
        anyone.add(null);
        anyone.add(UserRole.READER);
        anyone.add(UserRole.LIBRARIAN);
        anyone.add(UserRole.ADMIN);

        Set<UserRole> adminOnly = new HashSet<>();
        adminOnly.add(UserRole.ADMIN);

        Set<UserRole> readerOnly = new HashSet<>();
        readerOnly.add(UserRole.READER);

        Set<UserRole> librarianOnly = new HashSet<>();
        librarianOnly.add(UserRole.LIBRARIAN);

        Set<UserRole> readerAndLibrarianOnly = new HashSet<>();
        readerAndLibrarianOnly.add(UserRole.READER);
        readerAndLibrarianOnly.add(UserRole.LIBRARIAN);

        Set<UserRole> anyRole = new HashSet<>();
        anyRole.add(UserRole.READER);
        anyRole.add(UserRole.LIBRARIAN);
        anyRole.add(UserRole.ADMIN);

        commandAccessMap.put(CommandLineConstants.SIGN_IN_PAGE, anyone);
        commandAccessMap.put(CommandLineConstants.SIGN_IN, anyone);
        commandAccessMap.put(CommandLineConstants.SIGN_UP_PAGE, anyone);
        commandAccessMap.put(CommandLineConstants.SIGN_UP, anyone);
        commandAccessMap.put(CommandLineConstants.LANGUAGE_CHANGE, anyone);

        commandAccessMap.put(CommandLineConstants.SIGN_OUT, anyRole);
        commandAccessMap.put(CommandLineConstants.MAIN_PAGE, anyRole);
        commandAccessMap.put(CommandLineConstants.BOOKS_PAGE, anyRole);
        commandAccessMap.put(CommandLineConstants.SEARCH_BOOKS, anyRole);
        commandAccessMap.put(CommandLineConstants.VIEW_BOOK_PAGE, anyRole);

        commandAccessMap.put(CommandLineConstants.ORDERS_PAGE, readerAndLibrarianOnly);
        commandAccessMap.put(CommandLineConstants.VIEW_ORDER, readerAndLibrarianOnly);

        commandAccessMap.put(CommandLineConstants.ORDER_TO_READING_HALL, readerOnly);
        commandAccessMap.put(CommandLineConstants.ORDER_FOR_7_DAYS, readerOnly);
        commandAccessMap.put(CommandLineConstants.ORDER_FOR_14_DAYS, readerOnly);
        commandAccessMap.put(CommandLineConstants.ORDER_FOR_21_DAYS, readerOnly);
        commandAccessMap.put(CommandLineConstants.COLLECT_ORDER, readerOnly);
        commandAccessMap.put(CommandLineConstants.RETURN_ORDER, readerOnly);

        commandAccessMap.put(CommandLineConstants.DECLINE_ORDER, librarianOnly);
        commandAccessMap.put(CommandLineConstants.APPROVE_ORDER, librarianOnly);

        commandAccessMap.put(CommandLineConstants.EDIT_BOOK_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.DELETE_BOOK, adminOnly);
        commandAccessMap.put(CommandLineConstants.ADD_A_BOOK_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.ADD_BOOK, adminOnly);
        commandAccessMap.put(CommandLineConstants.USERS_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.SEARCH_USERS, adminOnly);
        commandAccessMap.put(CommandLineConstants.VIEW_USER_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.BLOCK_USER, adminOnly);
        commandAccessMap.put(CommandLineConstants.UNBLOCK_USER, adminOnly);
        commandAccessMap.put(CommandLineConstants.EDIT_USER_PAGE, adminOnly);
        commandAccessMap.put(CommandLineConstants.EDIT_USER, adminOnly);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(AttributeNameConstants.USER);

        String command = httpServletRequest.getParameter(ParameterNameConstants.COMMAND);


        UserRole userRole = user == null ? null : user.getRole();
        Set<UserRole> allowedRolesForCommand = commandAccessMap.get(command);
        if (!command.equals(CommandLineConstants.MAIN_PAGE) && allowedRolesForCommand != null && !allowedRolesForCommand.contains(userRole)) {
            httpServletResponse.sendRedirect(CommandInvocationConstants.MAIN_PAGE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
