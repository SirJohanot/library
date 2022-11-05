package com.patiun.library.validation;

import com.patiun.library.entity.book.Book;
import com.patiun.library.entity.book.Author;
import com.patiun.library.entity.book.Genre;
import com.patiun.library.entity.book.Publisher;
import com.patiun.library.exception.ValidationException;

import java.time.Year;
import java.util.List;

public class BookValidator extends AbstractValidator<Book> {

    private static final Year MIN_YEAR = Year.of(1000);
    private static final Year MAX_YEAR = Year.of(2025);

    @Override
    public void validate(Book object) throws ValidationException {
        String title = object.getTitle();
        throwExceptionIfNull(title, "A book's title must not be null");
        throwExceptionIfIsNotAWord(title, "A book's title must start with an alphabetical character");

        List<Author> authorList = object.getAuthorList();
        if (authorList == null || authorList.isEmpty()) {
            throw new ValidationException("A book must have at least one author");
        }
        for (Author author : authorList) {
            throwExceptionIfNull(author, "None of the book's authors may be null");
            String authorName = author.getName();
            throwExceptionIfNull(authorName, "An author's name must not be null");
            throwExceptionIfIsNotAWord(authorName, "An author's name must start with an alphabetical character");
        }

        Genre genre = object.getGenre();
        throwExceptionIfNull(genre, "A book must have a genre");
        String genreName = genre.getName();
        throwExceptionIfNull(genreName, "A genre's name must not be null");
        throwExceptionIfIsNotAWord(genreName, "A genre's name must start with an alphabetical character");

        Publisher publisher = object.getPublisher();
        throwExceptionIfNull(publisher, "A book must have a publisher");
        String publisherName = publisher.getName();
        throwExceptionIfNull(publisherName, "A publisher's name must not be null");
        throwExceptionIfIsNotAWord(publisherName, "A publisher's name must start with an alphabetical character");

        Year publishmentYear = object.getPublishmentYear();
        throwExceptionIfNull(publishmentYear, "A book must have a publishment year");
        if (publishmentYear.isAfter(MAX_YEAR) || publishmentYear.isBefore(MIN_YEAR)) {
            throw new ValidationException("A book's publishment year cannot be earlier than " + MIN_YEAR + " or later than " + MAX_YEAR);
        }

        Integer amount = object.getAmount();
        if (amount == null || amount < 0) {
            throw new ValidationException("A book's amount cannot be less than 0");
        }
    }

}
