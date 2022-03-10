package com.epam.library.command.parser;

import com.epam.library.entity.book.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorsLineParser {

    private static final String AUTHOR_DELIMITER = "[ ]+,[ ]+";

    public List<Author> parse(String authors) {
        List<Author> result = new ArrayList<>();
        for (String author : authors.split(AUTHOR_DELIMITER)) {
            Author newAuthor = Author.ofName(author);
            result.add(newAuthor);
        }
        return result;
    }
}
