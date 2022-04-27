package com.company.library.controller;

import com.company.library.command.Command;
import com.company.library.command.factory.CommandFactory;
import com.company.library.command.result.CommandResult;
import com.company.library.constant.ParameterNameConstants;
import com.company.library.constant.AttributeNameConstants;
import com.company.library.constant.PagePathConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (resp.isCommitted()) {
            return;
        }
        String commandLine = req.getParameter(ParameterNameConstants.COMMAND);
        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.createCommand(commandLine);
        try {
            CommandResult commandResult = command.execute(req, resp);
            processCommandResult(commandResult, req, resp);
        } catch (Exception e) {
            LOGGER.error(e);
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, e);
            processCommandResult(CommandResult.forward(PagePathConstants.ERROR), req, resp);
        }
    }

    private void processCommandResult(CommandResult commandResult, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String destination = commandResult.getPage();
        if (commandResult.isRedirect()) {
            resp.sendRedirect(destination);
        } else {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher(destination);
            dispatcher.forward(req, resp);
        }
    }
}
