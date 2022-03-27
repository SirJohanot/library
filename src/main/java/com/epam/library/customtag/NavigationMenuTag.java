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

    private static final String NAVIGATION_TAG_START = "<nav>" +
            "<form method=\"get\" action=\"controller?\">";
    private static final String BOOKS_PAGE_BUTTON = "<button type=\"submit\" name=\"command\" value=\"booksPage\">%s</button>";
    private static final String ADD_A_BOOK_PAGE_BUTTON = "<button type=\"submit\" name=\"command\" value=\"addABookPage\">%s</button>";
    private static final String USERS_PAGE_BUTTON = "<button type=\"submit\" name=\"command\" value=\"usersPage\">%s</button>";
    private static final String ORDERS_PAGE_BUTTON = "<button type=\"submit\" name=\"command\" value=\"ordersPage\">%s</button>";
    private static final String NAVIGATION_TAG_END = "</form>" +
            "</nav>";

    @Override
    public int doStartTag() throws JspException {
        StringBuilder completedNavigationTagBuilder = new StringBuilder(NAVIGATION_TAG_START);

        formatStringWithLineFromAttributesAndAddToBuilder(AttributeNameConstants.BOOKS_LOCALISATION, BOOKS_PAGE_BUTTON, completedNavigationTagBuilder);

        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(AttributeNameConstants.USER);
        UserRole role = user.getRole();
        switch (role) {
            case ADMIN:
                formatStringWithLineFromAttributesAndAddToBuilder(AttributeNameConstants.ADD_A_BOOK_LOCALISATION, ADD_A_BOOK_PAGE_BUTTON, completedNavigationTagBuilder);
                formatStringWithLineFromAttributesAndAddToBuilder(AttributeNameConstants.USERS_LOCALISATION, USERS_PAGE_BUTTON, completedNavigationTagBuilder);
                break;
            case LIBRARIAN:
                formatStringWithLineFromAttributesAndAddToBuilder(AttributeNameConstants.ORDERS_LOCALISATION, ORDERS_PAGE_BUTTON, completedNavigationTagBuilder);
                break;
            case READER:
                formatStringWithLineFromAttributesAndAddToBuilder(AttributeNameConstants.MY_ORDERS_LOCALISATION, ORDERS_PAGE_BUTTON, completedNavigationTagBuilder);
                break;
        }

        completedNavigationTagBuilder.append(NAVIGATION_TAG_END);
        
        String formattedNavigationTag = completedNavigationTagBuilder.toString();

        JspWriter writer = pageContext.getOut();
        try {
            writer.write(formattedNavigationTag);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private void formatStringWithLineFromAttributesAndAddToBuilder(String attributeName, String stringToFormat, StringBuilder builder) {
        String lineWithWhichToFormat = (String) pageContext.getAttribute(attributeName);
        String formattedString = String.format(stringToFormat, lineWithWhichToFormat);
        builder.append(formattedString);
    }
}
