package com.epam.library.parser;

import com.epam.library.entity.book.Author;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AuthorsLineParserTest {

    private final AuthorsLineParser authorsLineParser = new AuthorsLineParser();

    @Test
    public void testParseShouldIgnoreAllSpacesThatNeighbourTheCommandWhenTheAuthorsLineContainsMultipleCommas() {
        //given
        String authorsLine = "Leo Tolstoy     ,         Pavel Tsivunchyk      ,         George Orwell";
        Author firstAuthor = Author.ofName("Leo Tolstoy");
        Author secondAuthor = Author.ofName("Pavel Tsivunchyk");
        Author thirdAuthor = Author.ofName("George Orwell");
        List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor);
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }

    @Test
    public void testParseShouldParseTheAuthorsLineWhenItDoesNotHaveSpacesBeforeOfAfterAuthors() {
        //given
        String authorsLine = "Leo Tolstoy,Pavel Tsivunchyk,George Orwell";
        Author firstAuthor = Author.ofName("Leo Tolstoy");
        Author secondAuthor = Author.ofName("Pavel Tsivunchyk");
        Author thirdAuthor = Author.ofName("George Orwell");
        List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor);
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }

    @Test
    public void testParseShouldReturnListWithAnEmptyStringIfThereIsNoAuthorBetweenTwoCommas() {
        //given
        String authorsLine = "Leo Tolstoy,              ,George Orwell";
        Author firstAuthor = Author.ofName("Leo Tolstoy");
        Author secondAuthor = Author.ofName("");
        Author thirdAuthor = Author.ofName("George Orwell");
        List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor);
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }

    @Test
    public void testParseShouldReturnListWithEmptyStringsIfThereAreEmptyAuthors() {
        //given
        String authorsLine = "Leo Tolstoy,              ,,George Orwell";
        Author firstAuthor = Author.ofName("Leo Tolstoy");
        Author secondAuthor = Author.ofName("");
        Author thirdAuthor = Author.ofName("");
        Author fourthAuthor = Author.ofName("George Orwell");
        List<Author> authorList = Arrays.asList(firstAuthor, secondAuthor, thirdAuthor, fourthAuthor);
        //when
        List<Author> actualAuthorList = authorsLineParser.parse(authorsLine);
        //then
        Assert.assertEquals(authorList, actualAuthorList);
    }
}
