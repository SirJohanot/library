package com.epam.library.command;

import com.epam.library.command.result.CommandResult;
import com.epam.library.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Each new class that implements this interface is
 * created to extend the web app's functionality
 * without changing the already existing code
 */

public interface Command {

    /**
     * Uses Service objects to execute a specific task using data got from HttpServletRequest and HttpServletResponse objects
     *
     * @param req  HttpServletRequest, which contains data that needs to be processed
     * @param resp HttpServletResponse, which can be used to manipulate the response the client receives
     * @return CommandResult object, which field values determine the page to forward/redirect to and whether to forward or redirect
     * @throws ServiceException if a ServiceException is thrown by a Service object's method
     */
    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException;
}
