package com.epam.library.command;

import com.epam.library.dao.DaoHelperFactory;
import com.epam.library.service.UserServiceImpl;

public class CommandFactory {

    private static final String LOGIN_COMMAND_VALUE = "login";
    private static final String LANGUAGE_CHANGE_COMMAND_VALUE = "languageChange";

    public Command createCommand(String command) {
        switch (command) {
            case LOGIN_COMMAND_VALUE:
                return new LoginCommand(new UserServiceImpl(new DaoHelperFactory()));
            case LANGUAGE_CHANGE_COMMAND_VALUE:
                return new LanguageChangeCommand();
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
