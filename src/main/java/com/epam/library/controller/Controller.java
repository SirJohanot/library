package com.epam.library.controller;

import com.epam.library.command.Command;
import com.epam.library.command.CommandFactory;
import com.epam.library.command.CommandResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String commandLine = req.getParameter("command");
        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.createCommand(commandLine);
        try {
            CommandResult commandResult = command.execute(req, resp);
            processCommandResult(commandResult, req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
            processCommandResult(CommandResult.forward("/WEB-INF/view/errorPage.jsp"), req, resp);
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
