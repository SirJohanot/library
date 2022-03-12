package com.epam.library.parser;

import com.epam.library.command.parser.AuthorsLineParser;
import com.epam.library.entity.book.Author;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AuthorsLineParserTest {

    private final Author firstAuthor = Author.ofName("Leo Tolstoy");
    private final Author secondAuthor = Author.ofName("Pavel Tsivunchyk");
    private final Author thirdAuthor = Author.ofName("George Orwell");
    private final List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor);
    private final AuthorsLineParser authorsLineParser = new AuthorsLineParser();

    @Test
    public void testParseShouldIgnoreAllSpacesThatNeighbourTheCommandWhenTheAuthorsLineContainsMultipleCommas() {
        //given
        String authorsLine = "Leo Tolstoy     ,         Pavel Tsivunchyk      ,         George Orwell";
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }

    @Test
    public void testParseShouldParseTheAuthorsLineWhenItDoesNotHaveSpacesBeforeOfAfterAuthors() {
        //given
        String authorsLine = "Leo Tolstoy,Pavel Tsivunchyk,George Orwell";
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }
}
