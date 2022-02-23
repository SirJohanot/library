package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException;
}
