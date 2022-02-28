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
    private static final String SAVE_BOOK_COMMAND = "saveBook";
    private static final String ADD_A_BOOK_PAGE_COMMAND = "addABookPage";
    private static final String DELETE_BOOK_COMMAND = "deleteBook";
    private static final String USERS_PAGE_COMMAND = "usersPage";
    private static final String VIEW_USER_PAGE_COMMAND = "viewUser";
    private static final String EDIT_USER_PAGE_COMMAND = "editUserPage";
    private static final String SAVE_USER_COMMAND = "saveUser";
    private static final String BLOCK_USER_COMMAND = "blockUser";
    private static final String UNBLOCK_USER_COMMAND = "unblockUser";
    private static final String ORDER_TO_READING_HALL_COMMAND = "orderToReadingHallPage";
    private static final String ORDER_ON_SUBSCRIPTION_COMMAND = "orderOnSubscriptionPage";
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
            case SAVE_BOOK_COMMAND:
                return new SaveBookCommand(new BookServiceImpl(new DaoHelperFactory()));
            case DELETE_BOOK_COMMAND:
                return new DeleteBookCommand(new BookServiceImpl(new DaoHelperFactory()));
            case ADD_A_BOOK_PAGE_COMMAND:
                return new AddABookPageCommand();
            case USERS_PAGE_COMMAND:
                return new UsersPageCommand(new UserServiceImpl(new DaoHelperFactory()));
            case VIEW_USER_PAGE_COMMAND:
                return new ViewUserPageCommand(new UserServiceImpl(new DaoHelperFactory()));
            case EDIT_USER_PAGE_COMMAND:
                return new EditUserPageCommand(new UserServiceImpl(new DaoHelperFactory()));
            case SAVE_USER_COMMAND:
                return new SaveUserCommand(new UserServiceImpl(new DaoHelperFactory()));
            case BLOCK_USER_COMMAND:
                return new BlockUserCommand(new UserServiceImpl(new DaoHelperFactory()));
            case UNBLOCK_USER_COMMAND:
                return new UnblockUserCommand(new UserServiceImpl(new DaoHelperFactory()));
            case GLOBAL_ORDERS_PAGE_COMMAND:
                return new MainPageCommand();
            case USER_ORDERS_PAGE:
                return new MainPageCommand();
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
