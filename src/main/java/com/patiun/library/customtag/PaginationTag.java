package com.patiun.library.customtag;

import com.patiun.library.constant.AttributeNameConstants;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

    private static final String PAGINATION_TAG = "<div id=\"pagination\" class=\"round-bordered-subject\">" +
            "<form method=\"post\" action=\"controller?command=%s\">" +
            "<button type=\"submit\" name=\"page\" value=\"%d\">|&lt;</button>" +
            "</form>" +
            "<form method=\"post\" action=\"controller?command=%s\">" +
            "<button type=\"submit\" name=\"page\" value=\"%d\">&lt;</button>" +
            "</form>" +
            "<form method=\"post\" action=\"controller?command=%s\">" +
            "<input type=\"number\" min=\"1\" max=\"%d\" step=\"1\" name=\"page\" value=\"%d\"/>" +
            "</form>" +
            "<form method=\"post\" action=\"controller?command=%s\">" +
            "<button type=\"submit\" name=\"page\" value=\"%d\">&gt;</button>" +
            "</form>" +
            "<form method=\"post\" action=\"controller?command=%s\">" +
            "<button type=\"submit\" name=\"page\" value=\"%d\">&gt;|</button>" +
            "</form>" +
            "</div>";

    private static final Integer FIRST_PAGE = 1;

    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        Integer currentPage = (Integer) request.getAttribute(AttributeNameConstants.CURRENT_PAGE);
        Integer maxPage = (Integer) request.getAttribute(AttributeNameConstants.MAX_PAGE);

        Integer previousPage = Math.max(currentPage - 1, 1);
        Integer nextPage = Math.min(currentPage + 1, maxPage);

        String formattedPaginationTag = String.format(PAGINATION_TAG, command, FIRST_PAGE, command, previousPage, command, maxPage, currentPage, command, nextPage, command, maxPage);
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(formattedPaginationTag);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
