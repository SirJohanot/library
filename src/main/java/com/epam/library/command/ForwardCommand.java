package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardCommand implements Command {

    String path;

    public ForwardCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return CommandResult.forward(path);
    }
}
