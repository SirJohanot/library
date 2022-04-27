package com.company.library.validation;

import com.company.library.entity.User;
import com.company.library.entity.book.Book;
import com.company.library.entity.BookOrder;
import com.company.library.entity.enumeration.RentalState;
import com.company.library.entity.enumeration.RentalType;
import com.company.library.exception.ValidationException;
import org.junit.Test;

import java.sql.Date;

public class BookOrderValidatorTest {

    private final User user = User.ofId(3L);
    private final Book book = Book.ofId(1L);
    private final Date startDate = Date.valueOf("1956-3-12");
    private final Date endDate = Date.valueOf("1956-3-24");
    private final Date returnDate = Date.valueOf("1956-3-18");
    private final RentalType rentalType = RentalType.OUT_OF_LIBRARY;
    private final RentalState rentalState = RentalState.ORDER_PLACED;

    private final BookOrderValidator bookOrderValidator = new BookOrderValidator();

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenBookIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, null, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenUserIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, null, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenStartDateIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, user, null, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenEndDateIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, user, startDate, null, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenReturnDateIsEarlierThanStartDate() throws ValidationException {
        //given
        Date returnDate = Date.valueOf("1956-3-7");
        BookOrder bookOrder = new BookOrder(null, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenRentalTypeIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, user, startDate, endDate, returnDate, null, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenRentalStateIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, user, startDate, endDate, returnDate, rentalType, null);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }

    @Test
    public void testValidateShouldNotThrowValidationExceptionWhenOnlyIdIsNull() throws ValidationException {
        //given
        BookOrder bookOrder = new BookOrder(null, book, user, startDate, endDate, returnDate, rentalType, rentalState);
        //when
        bookOrderValidator.validate(bookOrder);
        //then
    }
}
