package com.epam.library.command.factory;

import com.epam.library.command.*;
import com.epam.library.command.book.AddABookPageCommand;
import com.epam.library.command.book.DeleteBookCommand;
import com.epam.library.command.book.EditBookPageCommand;
import com.epam.library.command.book.ViewBookPageCommand;
import com.epam.library.command.order.OrderStateAdvancementCommand;
import com.epam.library.command.order.ViewOrderPageCommand;
import com.epam.library.command.saving.*;
import com.epam.library.command.user.*;
import com.epam.library.command.viewentities.*;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.pagination.Paginator;
import com.epam.library.repository.RepositoryFactory;
import com.epam.library.service.BookOrderServiceImpl;
import com.epam.library.service.BookServiceImpl;
import com.epam.library.service.UserServiceImpl;

import static com.epam.library.constant.CommandLineConstants.*;

public class CommandFactory {

    public Command createCommand(String command) {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        switch (command) {
            case SIGN_IN_PAGE:
                return new SignInPageCommand();
            case SIGN_IN:
                return new SignInCommand(new UserServiceImpl(daoHelperFactory));
            case SIGN_UP_PAGE:
                return new SignUpPageCommand();
            case SIGN_UP:
                return new SignUpCommand(new UserServiceImpl(daoHelperFactory));
            case LANGUAGE_CHANGE:
                return new LanguageChangeCommand();
            case SIGN_OUT:
                return new SignOutCommand();
            case MAIN_PAGE:
                return new MainPageCommand();
            case BOOKS_PAGE:
                return new BooksPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case SEARCH_BOOKS:
                return new SearchBooksPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case VIEW_BOOK_PAGE:
                return new ViewBookPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case EDIT_BOOK_PAGE:
                return new EditBookPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case ADD_BOOK:
                return new AddABookCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case EDIT_BOOK:
                return new EditBookCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case DELETE_BOOK:
                return new DeleteBookCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case ADD_A_BOOK_PAGE:
                return new AddABookPageCommand();
            case USERS_PAGE:
                return new UsersPageCommand(new UserServiceImpl(daoHelperFactory), new Paginator<>());
            case SEARCH_USERS:
                return new SearchUsersPageCommand(new UserServiceImpl(daoHelperFactory), new Paginator<>());
            case VIEW_USER_PAGE:
                return new ViewUserPageCommand(new UserServiceImpl(daoHelperFactory));
            case EDIT_USER_PAGE:
                return new EditUserPageCommand(new UserServiceImpl(daoHelperFactory));
            case EDIT_USER:
                return new EditUserCommand(new UserServiceImpl(daoHelperFactory));
            case BLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory), true);
            case UNBLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory), false);
            case ORDERS_PAGE:
                return new OrdersPageCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case SEARCH_ORDERS:
                return new SearchOrdersPageCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case VIEW_ORDER:
                return new ViewOrderPageCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory));
            case ORDER_TO_READING_HALL:
                return new SaveOrderCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalType.TO_READING_HALL, 0);
            case ORDER_FOR_7_DAYS:
                return new SaveOrderCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalType.OUT_OF_LIBRARY, 7);
            case ORDER_FOR_14_DAYS:
                return new SaveOrderCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalType.OUT_OF_LIBRARY, 14);
            case ORDER_FOR_21_DAYS:
                return new SaveOrderCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalType.OUT_OF_LIBRARY, 21);
            case APPROVE_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.ORDER_APPROVED);
            case DECLINE_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.ORDER_DECLINED);
            case COLLECT_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.BOOK_COLLECTED);
            case RETURN_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.BOOK_RETURNED);
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
