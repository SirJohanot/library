package com.epam.library.controller;

import com.epam.library.command.Command;
import com.epam.library.command.factory.CommandFactory;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.PagePathConstants;
import com.epam.library.constant.ParameterNameConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller extends HttpServlet {

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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            req.setAttribute(AttributeNameConstants.ERROR_MESSAGE, sw.toString());
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
