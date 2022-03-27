package com.epam.library.specification;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;

import java.time.Year;
import java.util.List;

public class BookContainsLineSpecification extends AbstractChainedSpecification<Book> {

    private final String lineToContain;

    public BookContainsLineSpecification(String lineToContain) {
        this.lineToContain = lineToContain;
    }

    public BookContainsLineSpecification(String lineToContain, Specification<Book> successor) {
        super(successor);
        this.lineToContain = lineToContain;
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(Book object) {
        String title = object.getTitle();
        if (title.contains(lineToContain)) {
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
        if (authorsLine.contains(lineToContain)) {
            return true;
        }

        Genre genre = object.getGenre();
        String genreName = genre.getName();
        if (genreName.contains(lineToContain)) {
            return true;
        }

        Publisher publisher = object.getPublisher();
        String publisherName = publisher.getName();
        if (publisherName.contains(lineToContain)) {
            return true;
        }

        Year publishmentYear = object.getPublishmentYear();
        String publishmentYearLine = publishmentYear.toString();
        return publishmentYearLine.contains(lineToContain);
    }
}
