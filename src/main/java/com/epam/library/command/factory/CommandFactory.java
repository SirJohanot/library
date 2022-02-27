package com.epam.library.command.factory;

import com.epam.library.command.*;
import com.epam.library.dao.daohelper.DaoHelperFactory;
import com.epam.library.service.BookServiceImpl;
import com.epam.library.service.UserServiceImpl;

public class CommandFactory {

    private static final String LOGIN_COMMAND = "signIn";
    private static final String LANGUAGE_CHANGE_COMMAND = "languageChange";
    private static final String LOG_OUT_COMMAND = "signOut";
    private static final String SIGN_IN_PAGE_COMMAND = "signInPage";
    private static final String MAIN_PAGE_COMMAND = "mainPage";
    private static final String BOOKS_PAGE_COMMAND = "booksPage";
    private static final String VIEW_BOOK_PAGE_COMMAND = "viewBook";
    private static final String EDIT_BOOK_PAGE_COMMAND = "editBookPage";
    private static final String EDIT_BOOK_COMMAND = "editBook";
    private static final String ADD_A_BOOK_PAGE_COMMAND = "addABookPage";
    private static final String DELETE_BOOK_COMMAND = "deleteBook";
    private static final String USERS_PAGE_COMMAND = "usersPage";
    private static final String GLOBAL_ORDERS_PAGE_COMMAND = "globalOrdersPage";
    private static final String USER_ORDERS_PAGE = "userOrdersPage";

    public Command createCommand(String command) {
        switch (command) {
            case LOGIN_COMMAND:
                return new SignInCommand(new UserServiceImpl(new DaoHelperFactory()));
            case LANGUAGE_CHANGE_COMMAND:
                return new LanguageChangeCommand();
            case LOG_OUT_COMMAND:
                return new SignOutCommand();
            case SIGN_IN_PAGE_COMMAND:
                return new SignInPageCommand();
            case MAIN_PAGE_COMMAND:
                return new MainPageCommand();
            case BOOKS_PAGE_COMMAND:
                return new BooksPageCommand(new BookServiceImpl(new DaoHelperFactory()));   //TODO: create proper commands
            case VIEW_BOOK_PAGE_COMMAND:
                return new ViewBookPageCommand(new BookServiceImpl(new DaoHelperFactory()));
            case EDIT_BOOK_PAGE_COMMAND:
                return new EditBookPageCommand(new BookServiceImpl(new DaoHelperFactory()));
            case EDIT_BOOK_COMMAND:
                return new EditBookCommand(new BookServiceImpl(new DaoHelperFactory()));
            case DELETE_BOOK_COMMAND:
                return new DeleteBookCommand(new BookServiceImpl(new DaoHelperFactory()));
            case ADD_A_BOOK_PAGE_COMMAND:
                return new MainPageCommand();
            case USERS_PAGE_COMMAND:
                return new MainPageCommand();
            case GLOBAL_ORDERS_PAGE_COMMAND:
                return new MainPageCommand();
            case USER_ORDERS_PAGE:
                return new MainPageCommand();
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
