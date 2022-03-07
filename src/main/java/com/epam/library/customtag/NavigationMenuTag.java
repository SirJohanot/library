package com.epam.library.customtag;

import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class NavigationMenuTag extends TagSupport {

    private static final String NAV_START = "<nav>";
    private static final String FORM_START = "<form method=\"get\" action=\"controller?\">";
    private static final String BOOKS_PAGE_BUTTON_START = "<button type=\"submit\" name=\"command\" value=\"booksPage\">";
    private static final String ADD_A_BOOK_PAGE_BUTTON_START = "<button type=\"submit\" name=\"command\" value=\"addABookPage\">";
    private static final String USERS_PAGE_BUTTON_START = "<button type=\"submit\" name=\"command\" value=\"usersPage\">";
    private static final String ORDERS_PAGE_BUTTON_START = "<button type=\"submit\" name=\"command\" value=\"globalOrdersPage\">";
    private static final String MY_ORDERS_PAGE_BUTTON_START = "<button type=\"submit\" name=\"command\" value=\"userOrdersPage\">";
    private static final String BUTTON_END = "</button>";
    private static final String FORM_END = "</form>";
    private static final String NAV_END = "</nav>";

    @Override
    public int doStartTag() throws JspException {
        String books = (String) pageContext.getAttribute(AttributeNameConstants.BOOKS_LOCALISATION);
        String addABook = (String) pageContext.getAttribute(AttributeNameConstants.ADD_A_BOOK_LOCALISATION);
        String users = (String) pageContext.getAttribute(AttributeNameConstants.USERS_LOCALISATION);
        String orders = (String) pageContext.getAttribute(AttributeNameConstants.ORDERS_LOCALISATION);
        String myOrders = (String) pageContext.getAttribute(AttributeNameConstants.MY_ORDERS_LOCALISATION);

        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute("user");
        UserRole role = user.getRole();

        JspWriter writer = pageContext.getOut();
        try {
            writer.write(NAV_START);
            writer.write(FORM_START);
            writeMultiple(writer, BOOKS_PAGE_BUTTON_START, books, BUTTON_END);
            switch (role) {
                case ADMIN:
                    writeMultiple(writer, ADD_A_BOOK_PAGE_BUTTON_START, addABook, BUTTON_END);
                    writeMultiple(writer, USERS_PAGE_BUTTON_START, users, BUTTON_END);
                    break;
                case LIBRARIAN:
                    writeMultiple(writer, ORDERS_PAGE_BUTTON_START, orders, BUTTON_END);
                    break;
                case READER:
                    writeMultiple(writer, MY_ORDERS_PAGE_BUTTON_START, myOrders, BUTTON_END);
                    break;
            }
            writer.write(FORM_END);
            writer.write(NAV_END);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private void writeMultiple(JspWriter writer, String... lines) throws IOException {
        for (String line : lines) {
            writer.write(line);
        }
    }
}
