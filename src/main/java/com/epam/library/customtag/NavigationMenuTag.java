package com.epam.library.customtag;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class NavigationMenuTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("user");
        UserRole role = user.getRole();
        return SKIP_BODY;   //TODO: implement the custom tag
    }
}
