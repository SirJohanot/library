package com.company.library.command.factory;

import com.company.library.assembler.AssemblerFactory;
import com.company.library.command.Command;
import com.company.library.command.ForwardCommand;
import com.company.library.command.LanguageChangeCommand;
import com.company.library.command.MainPageCommand;
import com.company.library.command.book.DeleteBookCommand;
import com.company.library.command.book.EditBookPageCommand;
import com.company.library.command.book.ViewBookPageCommand;
import com.company.library.command.order.OrderStateAdvancementCommand;
import com.company.library.command.order.ViewOrderPageCommand;
import com.company.library.command.saving.*;
import com.company.library.command.user.*;
import com.company.library.command.viewentities.*;
import com.company.library.constant.PagePathConstants;
import com.company.library.dao.helper.DaoHelperFactory;
import com.company.library.pagination.Paginator;
import com.company.library.parser.AuthorsLineParser;
import com.company.library.service.BookOrderServiceImpl;
import com.company.library.service.BookServiceImpl;
import com.company.library.service.UserServiceImpl;
import com.company.library.validation.BookOrderValidator;
import com.company.library.validation.BookValidator;
import com.company.library.validation.PasswordValidator;
import com.company.library.validation.UserValidator;

import static com.company.library.constant.CommandLineConstants.*;

public class CommandFactory {

    public Command createCommand(String command) {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
        AssemblerFactory assemblerFactory = new AssemblerFactory();
        switch (command) {
            case SIGN_IN_PAGE:
                return new ForwardCommand(PagePathConstants.SIGN_IN);
            case SIGN_IN:
                return new SignInCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()));
            case SIGN_UP_PAGE:
                return new ForwardCommand(PagePathConstants.SIGN_UP);
            case SIGN_UP:
                return new SignUpCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()));
            case LANGUAGE_CHANGE:
                return new LanguageChangeCommand();
            case SIGN_OUT:
                return new SignOutCommand();
            case MAIN_PAGE:
                return new MainPageCommand();
            case BOOKS_PAGE:
                return new BooksPageCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()), new Paginator<>());
            case SEARCH_BOOKS:
                return new SearchBooksPageCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()), new Paginator<>());
            case VIEW_BOOK_PAGE:
                return new ViewBookPageCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()));
            case EDIT_BOOK_PAGE:
                return new EditBookPageCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()));
            case ADD_BOOK:
                return new AddABookCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()));
            case EDIT_BOOK:
                return new EditBookCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()));
            case DELETE_BOOK:
                return new DeleteBookCommand(new BookServiceImpl(daoHelperFactory, assemblerFactory, new BookValidator(), new AuthorsLineParser()));
            case ADD_A_BOOK_PAGE:
                return new ForwardCommand(PagePathConstants.ADD_A_BOOK);
            case USERS_PAGE:
                return new UsersPageCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()), new Paginator<>());
            case SEARCH_USERS:
                return new SearchUsersPageCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()), new Paginator<>());
            case VIEW_USER_PAGE:
                return new ViewUserPageCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()));
            case EDIT_USER_PAGE:
                return new EditUserPageCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()));
            case EDIT_USER:
                return new EditUserCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()));
            case BLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()), true);
            case UNBLOCK_USER:
                return new SetUserBlockedStatusCommand(new UserServiceImpl(daoHelperFactory, new UserValidator(), new PasswordValidator()), false);
            case ORDERS_PAGE:
                return new OrdersPageCommand(new BookOrderServiceImpl(daoHelperFactory, assemblerFactory, new BookOrderValidator()), new Paginator<>());
            case SEARCH_ORDERS:
                return new SearchOrdersPageCommand(new BookOrderServiceImpl(daoHelperFactory, assemblerFactory, new BookOrderValidator()), new Paginator<>());
            case VIEW_ORDER:
                return new ViewOrderPageCommand(new BookOrderServiceImpl(daoHelperFactory, assemblerFactory, new BookOrderValidator()));
            case PLACE_ORDER:
                return new SaveOrderCommand(new BookOrderServiceImpl(daoHelperFactory, assemblerFactory, new BookOrderValidator()));
            case ADVANCE_ORDER:
                return new OrderStateAdvancementCommand(new BookOrderServiceImpl(daoHelperFactory, assemblerFactory, new BookOrderValidator()));
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
