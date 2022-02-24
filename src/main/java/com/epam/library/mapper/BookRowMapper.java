package com.epam.library.mapper;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Book.ID_COLUMN);
        String title = resultSet.getString(Book.TITLE_COLUMN);
        Long genreId = resultSet.getLong(Book.GENRE_ID_COLUMN);
        Long publisherId = resultSet.getLong(Book.PUBLISHER_ID_COLUMN);
        Year publishmentYear = Year.parse(resultSet.getString(Book.PUBLISHMENT_YEAR_COLUMN));
        int amount = resultSet.getInt(Book.AMOUNT_COLUMN);
        Genre genre = new Genre(genreId, null);
        Publisher publisher = new Publisher(publisherId, null);
        return new Book(id, title, new ArrayList<>(), genre, publisher, publishmentYear, amount);
    }
}
