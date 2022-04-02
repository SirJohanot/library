package com.epam.library.specification;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;

import java.time.Year;
import java.util.List;

public class BookContainsLineSpecification extends AbstractChainedContainsLineSpecification<Book> {

    public BookContainsLineSpecification(String targetLine) {
        super(targetLine);
    }

    public BookContainsLineSpecification(Specification<Book> successor, String targetLine) {
        super(successor, targetLine);
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(Book object) {
        String title = object.getTitle();
        if (containsTargetLineIgnoreCase(title)) {
            return true;
        }

        List<Author> authorList = object.getAuthorList();
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author : authorList) {
            String authorName = author.getName();
            stringBuilder.append(authorName);
            stringBuilder.append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        String authorsLine = stringBuilder.toString();
        if (containsTargetLineIgnoreCase(authorsLine)) {
            return true;
        }

        Genre genre = object.getGenre();
        String genreName = genre.getName();
        if (containsTargetLineIgnoreCase(genreName)) {
            return true;
        }

        Publisher publisher = object.getPublisher();
        String publisherName = publisher.getName();
        if (containsTargetLineIgnoreCase(publisherName)) {
            return true;
        }

        Year publishmentYear = object.getPublishmentYear();
        String publishmentYearLine = publishmentYear.toString();
        return containsTargetLineIgnoreCase(publishmentYearLine);
    }
}
