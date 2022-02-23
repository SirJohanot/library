package com.epam.library.command;

import com.epam.library.dao.DaoHelperFactory;
import com.epam.library.service.UserServiceImpl;

public class CommandFactory {

    private static final String LOGIN_COMMAND_VALUE = "login";
    private static final String LANGUAGE_CHANGE_COMMAND_VALUE = "languageChange";
    private static final String LOG_OUT_COMMAND = "signOut";
    private static final String SIGN_IN_PAGE_COMMAND = "signInPage";
    private static final String MAIN_PAGE_COMMAND = "mainPage";

    public Command createCommand(String command) {
        switch (command) {
            case LOGIN_COMMAND_VALUE:
                return new LoginCommand(new UserServiceImpl(new DaoHelperFactory()));
            case LANGUAGE_CHANGE_COMMAND_VALUE:
                return new LanguageChangeCommand();
            case LOG_OUT_COMMAND:
                return new SignOutCommand();
            case SIGN_IN_PAGE_COMMAND:
                return new SignInPageCommand();
            case MAIN_PAGE_COMMAND:
                return new MainPageCommand();
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
