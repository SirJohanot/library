package com.epam.library.command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageChangeCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String newLocale = req.getParameter("locale");
        HttpSession session = req.getSession();
        session.setAttribute("locale", newLocale);
        ServletContext context = session.getServletContext();
        return (String) req.getAttribute("javax.servlet.forward.request_uri");    //I need to somehow make this command tell the Controller to forward the req, resp to the same .jsp file
    }
}
