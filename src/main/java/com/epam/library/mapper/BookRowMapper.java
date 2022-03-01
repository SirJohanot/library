package com.epam.library.mapper;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;

public class BookRowMapper implements RowMapper<Book> {

    private static final String NON_DIGIT_REGEX = "[\\D]";

    @Override
    public Book map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Book.ID_COLUMN);
        String title = resultSet.getString(Book.TITLE_COLUMN);
        Long genreId = resultSet.getLong(Book.GENRE_ID_COLUMN);
        Genre genre = Genre.ofId(genreId);
        Long publisherId = resultSet.getLong(Book.PUBLISHER_ID_COLUMN);
        Publisher publisher = Publisher.ofId(publisherId);
        String yearLine = resultSet.getString(Book.PUBLISHMENT_YEAR_COLUMN).split(NON_DIGIT_REGEX)[0];
        Year publishmentYear = Year.parse(yearLine);
        Integer amount = resultSet.getInt(Book.AMOUNT_COLUMN);
        return new Book(id, title, new ArrayList<>(), genre, publisher, publishmentYear, amount);
    }
}
