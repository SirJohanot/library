package com.epam.library.customtag;

import com.epam.library.constant.AttributeNameConstants;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

    private static final String PAGINATION_START = "<div id=\"pagination-form\" class=\"round-bordered-subject\">";
    private static final String FORM_START_BEGINNING = "<form method=\"post\" action=\"controller?command=";
    private static final String FORM_START_END = "\">";
    private static final String FIRST_PAGE_BUTTON = "<button type=\"submit\" name=\"page\" value=\"1\">|&lt;</button>";
    private static final String CUSTOM_VALUE_BUTTON_START = "<button type=\"submit\" name=\"page\" value=\"";
    private static final String PREVIOUS_PAGE_BUTTON_END = "\">&lt;</button>";
    private static final String CUSTOM_PAGE_INPUT_FIELD_START = "<input type=\"number\" min=\"1\" max=\"${requestScope.maxPage}\" step=\"1\" name=\"page\" value=\"";
    private static final String CUSTOM_PAGE_INPUT_FIELD_END = "\"/>";
    private static final String NEXT_PAGE_BUTTON_END = "\">&gt;</button>";
    private static final String LAST_PAGE_BUTTON_END = "\">&gt;|</button>";
    private static final String BUTTON_END = "</button>";
    private static final String FORM_END = "</form>";
    private static final String PAGINATION_END = "</div>";

    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        Integer currentPage = (Integer) request.getAttribute(AttributeNameConstants.CURRENT_PAGE);
        Integer maxPage = (Integer) request.getAttribute(AttributeNameConstants.MAX_PAGE);

        JspWriter writer = pageContext.getOut();
        try {
            writer.write(PAGINATION_START);

            writeMultiple(writer, FORM_START_BEGINNING, command, FORM_START_END);
            writer.write(FIRST_PAGE_BUTTON);
            writer.write(FORM_END);

            writeMultiple(writer, FORM_START_BEGINNING, command, FORM_START_END);
            writeMultiple(writer, CUSTOM_VALUE_BUTTON_START, String.valueOf(currentPage - 1), PREVIOUS_PAGE_BUTTON_END);
            writer.write(FORM_END);

            writeMultiple(writer, FORM_START_BEGINNING, command, FORM_START_END);
            writeMultiple(writer, CUSTOM_PAGE_INPUT_FIELD_START, String.valueOf(currentPage), CUSTOM_PAGE_INPUT_FIELD_END);
            writer.write(FORM_END);

            writeMultiple(writer, FORM_START_BEGINNING, command, FORM_START_END);
            writeMultiple(writer, CUSTOM_VALUE_BUTTON_START, String.valueOf(currentPage + 1), NEXT_PAGE_BUTTON_END);
            writer.write(FORM_END);

            writeMultiple(writer, FORM_START_BEGINNING, command, FORM_START_END);
            writeMultiple(writer, CUSTOM_VALUE_BUTTON_START, String.valueOf(maxPage), LAST_PAGE_BUTTON_END);
            writer.write(FORM_END);

            writer.write(PAGINATION_END);
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
