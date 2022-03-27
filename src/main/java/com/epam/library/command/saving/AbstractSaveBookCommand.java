package com.epam.library.command.saving;

import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.time.Year;

public abstract class AbstractSaveBookCommand extends AbstractSaveCommand {

    private final BookService bookService;

    public AbstractSaveBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void saveUsingService(HttpServletRequest req) throws ValidationException, ServiceException {
        String bookIdLine = req.getParameter(ParameterNameConstants.BOOK_ID);
        Long bookId = bookIdLine == null ? null : Long.valueOf(bookIdLine);

        String title = req.getParameter(ParameterNameConstants.BOOK_TITLE);
        String authors = req.getParameter(ParameterNameConstants.BOOK_AUTHORS);
        String genre = req.getParameter(ParameterNameConstants.BOOK_GENRE);
        String publisher = req.getParameter(ParameterNameConstants.BOOK_PUBLISHER);

        String publishmentYearLine = req.getParameter(ParameterNameConstants.BOOK_PUBLISHMENT_YEAR);
        Year publishmentYear = Year.parse(publishmentYearLine);

        String amountLine = req.getParameter(ParameterNameConstants.BOOK_AMOUNT);
        Integer amount = Integer.valueOf(amountLine);

        bookService.saveBook(bookId, title, authors, genre, publisher, publishmentYear, amount);
    }
}
