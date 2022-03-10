package com.epam.library.command.validation;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.ValidationException;

import java.util.List;
import java.util.regex.Pattern;

public class BookValidator implements Validator<Book> {

    private static final String IS_A_WORD_REGEX = "[\\p{L}]+[.]*";

    @Override
    public void validate(Book object) throws ValidationException {
        String title = object.getTitle();
        if (isNotAWord(title)) {
            throw new ValidationException("A book's title must start with an alphabetical character");
        }

        List<Author> authorList = object.getAuthorList();
        for (Author author : authorList) {
            if (isNotAWord(author.getName())) {
                throw new ValidationException("An author's name must start with an alphabetical character");
            }
        }

        Genre genre = object.getGenre();
        String genreName = genre.getName();
        if (isNotAWord(genreName)) {
            throw new ValidationException("A genre's name must start with an alphabetical character");
        }

        Publisher publisher = object.getPublisher();
        String publisherName = publisher.getName();
        if (isNotAWord(publisherName)) {
            throw new ValidationException("A publisher's name must start with an alphabetical character");
        }

        if (object.getAmount() < 0) {
            throw new ValidationException("A book's amount cannot be less than 0");
        }
    }

    private boolean isNotAWord(String line) {
        return !Pattern.matches(IS_A_WORD_REGEX, line);
    }
}
