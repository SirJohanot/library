package com.epam.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageChangeCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String newLocale = req.getParameter("locale");
        HttpSession session = req.getSession();
        session.setAttribute("locale", newLocale);
        return CommandResult.forward("/index.jsp");    //I need to somehow make this command tell the Controller to forward the req, resp to the same .jsp file
    }
}
