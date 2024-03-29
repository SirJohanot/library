package com.patiun.library.validation;

import com.patiun.library.entity.User;
import com.patiun.library.entity.book.Book;
import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.entity.enumeration.RentalType;
import com.patiun.library.exception.ValidationException;

import java.sql.Date;

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
