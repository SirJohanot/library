package com.epam.library.command.validation;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;
import com.epam.library.exception.ValidationException;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BookOrderValidator extends AbstractValidator<BookOrder> {

    @Override
    public void validate(BookOrder object) throws ValidationException {
        User user = object.getUser();
        throwExceptionIfNull(user, "A book order must have its user");
        Long userId = user.getId();
        if (userId == null || userId < 1) {
            throw new ValidationException("The user of a book order must have a positive id");
        }

        Book book = object.getBook();
        throwExceptionIfNull(book, "A book order must have its book");
        Long bookId = book.getId();
        if (bookId == null || bookId < 1) {
            throw new ValidationException("The book of a book order must have a positive id");
        }

        DateFormat dateFormat = new SimpleDateFormat();

        Date startDate = object.getStartDate();
        throwExceptionIfNull(startDate, "A book order's start date cannot be null");

        Date endDate = object.getEndDate();
        throwExceptionIfNull(endDate, "A book order's end date cannot be null");

        if (startDate.after(endDate)) {
            throw new ValidationException("A book order's start date cannot be later than end date");
        }

        Date returnDate = object.getReturnDate();
        if (returnDate != null && returnDate.before(startDate)) {
            throw new ValidationException("A book order's return date cannot be earlier than start date");
        }

        RentalType rentalType = object.getType();
        throwExceptionIfNull(rentalType, "A book order must have a type");

        RentalState rentalState = object.getState();
        throwExceptionIfNull(rentalState, "A book order must have a state");
    }

}
