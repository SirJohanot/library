package com.epam.library.command.factory;

import com.epam.library.command.Command;
import com.epam.library.command.LanguageChangeCommand;
import com.epam.library.command.MainPageCommand;
import com.epam.library.command.SignInPageCommand;
import com.epam.library.command.book.*;
import com.epam.library.command.order.OrderOnSubscriptionCommand;
import com.epam.library.command.order.OrderToReadingHallCommand;
import com.epam.library.command.order.OrdersPageCommand;
import com.epam.library.command.order.statemanipulation.LibrarianOrderDecisionCommand;
import com.epam.library.command.order.statemanipulation.ReaderOrderActionCommand;
import com.epam.library.command.user.*;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.service.BookOrderServiceImpl;
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
    private static final String USER_ORDERS_PAGE_COMMAND = "userOrdersPage";
    private static final String GLOBAL_ORDERS_PAGE_COMMAND = "globalOrdersPage";
    private static final String ORDERS_PAGE_COMMAND = "ordersPage";
    private static final String ORDER_TO_READING_HALL_COMMAND = "orderToReadingHallPage";
    private static final String ORDER_ON_SUBSCRIPTION_COMMAND = "orderOnSubscriptionPage";
    private static final String ORDER_COMMAND = "order";
    private static final String APPROVE_ORDER_COMMAND = "approveOrder";
    private static final String DECLINE_ORDER_COMMAND = "declineOrder";
    private static final String COLLECT_ORDER_COMMAND = "collectOrder";
    private static final String RETURN_ORDER_COMMAND = "returnOrder";

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
                return new BooksPageCommand(new BookServiceImpl(new DaoHelperFactory()));
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
            case USER_ORDERS_PAGE_COMMAND:
            case GLOBAL_ORDERS_PAGE_COMMAND:
            case ORDERS_PAGE_COMMAND:
                return new OrdersPageCommand(new BookOrderServiceImpl(new DaoHelperFactory()));
            case ORDER_TO_READING_HALL_COMMAND:
                return new OrderToReadingHallCommand(new BookServiceImpl(new DaoHelperFactory()), new BookOrderServiceImpl(new DaoHelperFactory()));
            case ORDER_ON_SUBSCRIPTION_COMMAND:
                return new OrderOnSubscriptionCommand(new BookServiceImpl(new DaoHelperFactory()), new BookOrderServiceImpl(new DaoHelperFactory()));
            case APPROVE_ORDER_COMMAND:
                return new LibrarianOrderDecisionCommand(new BookOrderServiceImpl(new DaoHelperFactory()), RentalState.ORDER_APPROVED);
            case DECLINE_ORDER_COMMAND:
                return new LibrarianOrderDecisionCommand(new BookOrderServiceImpl(new DaoHelperFactory()), RentalState.ORDER_DECLINED);
            case COLLECT_ORDER_COMMAND:
                return new ReaderOrderActionCommand(new BookOrderServiceImpl(new DaoHelperFactory()), RentalState.BOOK_COLLECTED);
            case RETURN_ORDER_COMMAND:
                return new ReaderOrderActionCommand(new BookOrderServiceImpl(new DaoHelperFactory()), RentalState.BOOK_RETURNED);
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
