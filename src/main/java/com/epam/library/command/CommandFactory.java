package com.epam.library.command;

import com.epam.library.dao.DaoHelperFactory;
import com.epam.library.service.UserServiceImpl;

public class CommandFactory {

    public Command createCommand(String command) {
        switch (command) {
            case "login":
                return new LoginCommand(new UserServiceImpl(new DaoHelperFactory()));
            case "language-change":
                return new LanguageChangeCommand();
            default:
                throw new IllegalArgumentException("Unknown command = " + command);
        }
    }
}
