package com.epam.library.command.factory;

import com.epam.library.command.Command;
import com.epam.library.command.LanguageChangeCommand;
import com.epam.library.command.MainPageCommand;
import com.epam.library.command.SignInPageCommand;
import com.epam.library.command.book.*;
import com.epam.library.command.order.*;
import com.epam.library.command.repository.RepositoryFactory;
import com.epam.library.command.user.*;
import com.epam.library.command.viewentities.BooksPageCommand;
import com.epam.library.command.viewentities.OrdersPageCommand;
import com.epam.library.command.viewentities.UsersPageCommand;
import com.epam.library.constant.CommandLineConstants;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.pagination.Paginator;
import com.epam.library.service.BookOrderServiceImpl;
import com.epam.library.service.BookServiceImpl;
import com.epam.library.service.UserServiceImpl;

public class CommandFactory {

    public Command createCommand(String command) {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        switch (command) {
            case CommandLineConstants.SIGN_IN:
                return new SignInCommand(new UserServiceImpl(daoHelperFactory));
            case CommandLineConstants.LANGUAGE_CHANGE:
                return new LanguageChangeCommand();
            case CommandLineConstants.SIGN_OUT:
                return new SignOutCommand();
            case CommandLineConstants.SIGN_IN_PAGE:
                return new SignInPageCommand();
            case CommandLineConstants.MAIN_PAGE:
                return new MainPageCommand();
            case CommandLineConstants.BOOKS_PAGE:
                return new BooksPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case CommandLineConstants.VIEW_BOOK_PAGE:
                return new ViewBookPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.EDIT_BOOK_PAGE:
                return new EditBookPageCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.SAVE_BOOK:
                return new SaveBookCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.DELETE_BOOK:
                return new DeleteBookCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.ADD_A_BOOK_PAGE:
                return new AddABookPageCommand();
            case CommandLineConstants.USERS_PAGE:
                return new UsersPageCommand(new UserServiceImpl(daoHelperFactory), new Paginator<>());
            case CommandLineConstants.VIEW_USER_PAGE:
                return new ViewUserPageCommand(new UserServiceImpl(daoHelperFactory));
            case CommandLineConstants.EDIT_USER_PAGE:
                return new EditUserPageCommand(new UserServiceImpl(daoHelperFactory));
            case CommandLineConstants.SAVE_USER:
                return new SaveUserCommand(new UserServiceImpl(daoHelperFactory));
            case CommandLineConstants.BLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory), true);
            case CommandLineConstants.UNBLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory), false);
            case CommandLineConstants.USER_ORDERS_PAGE:
            case CommandLineConstants.GLOBAL_ORDERS_PAGE:
            case CommandLineConstants.ORDERS_PAGE:
                return new OrdersPageCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), new Paginator<>());
            case CommandLineConstants.VIEW_ORDER:
                return new ViewOrderPage(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.ORDER_TO_READING_HALL:
                return new OrderToReadingHallCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory), new BookOrderServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.ORDER_ON_SUBSCRIPTION:
                return new OrderOnSubscriptionCommand(new BookServiceImpl(daoHelperFactory, repositoryFactory), new BookOrderServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.ORDER:
                return new OrderCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory));
            case CommandLineConstants.APPROVE_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.ORDER_APPROVED);
            case CommandLineConstants.DECLINE_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.ORDER_DECLINED);
            case CommandLineConstants.COLLECT_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.BOOK_COLLECTED);
            case CommandLineConstants.RETURN_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, repositoryFactory), RentalState.BOOK_RETURNED);
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
