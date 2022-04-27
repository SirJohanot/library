package com.company.library.validation;

import com.company.library.entity.book.Book;
import com.company.library.entity.book.Author;
import com.company.library.entity.book.Genre;
import com.company.library.entity.book.Publisher;
import com.company.library.exception.ValidationException;
import org.junit.Test;

import java.time.Year;
import java.util.List;

public class BookValidatorTest {

    private final String title = "War And Peace";
    private final List<Author> authorList = List.of(Author.ofName("author"));
    private final Genre genre = Genre.ofName("genre");
    private final Publisher publisher = Publisher.ofName("publisher");
    private final Year publishmentYear = Year.of(1984);
    private final Integer amount = 3;

    private final BookValidator bookValidator = new BookValidator();

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenTitleDoesNotStartWithAWordCharacter() throws ValidationException {
        //given
        String title = ".,invalidTitle";

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenAuthorDoesNotStartWithAWordCharacter() throws ValidationException {
        //given
        List<Author> authorList = List.of(Author.ofName(";Author"));

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenGenreDoesNotStartWithAWordCharacter() throws ValidationException {
        //given
        Genre genre = Genre.ofName("'genre'");

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenPublisherDoesNotStartWithAWordCharacter() throws ValidationException {
        //given
        Publisher publisher = Publisher.ofName("~idf");

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenBookDoesNotHaveAPublishmentYear() throws ValidationException {
        //given
        Book book = new Book(null, title, authorList, genre, publisher, null, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowValidationExceptionWhenAmountIsLessThenZero() throws ValidationException {
        //given
        Integer amount = -2;

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test
    public void testValidateShouldNotThrowValidationExceptionForEnglishTitle() throws ValidationException {
        //given
        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

    @Test
    public void testValidateShouldNotThrowValidationExceptionForCyrillicTitle() throws ValidationException {
        //given
        String title = "Прохожий";

        Book book = new Book(null, title, authorList, genre, publisher, publishmentYear, amount);
        //when
        bookValidator.validate(book);
        //then
    }

}
