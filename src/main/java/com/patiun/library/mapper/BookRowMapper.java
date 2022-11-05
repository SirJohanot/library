package com.patiun.library.mapper;

import com.patiun.library.entity.book.Book;
import com.patiun.library.entity.book.Genre;
import com.patiun.library.entity.book.Publisher;

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
        Genre genre = Genre.ofId(genreId);

        Long publisherId = resultSet.getLong(Book.PUBLISHER_ID_COLUMN);
        Publisher publisher = Publisher.ofId(publisherId);

        int yearNumber = resultSet.getInt(Book.PUBLISHMENT_YEAR_COLUMN);
        Year publishmentYear = Year.of(yearNumber);

        Integer amount = resultSet.getInt(Book.AMOUNT_COLUMN);
        return new Book(id, title, new ArrayList<>(), genre, publisher, publishmentYear, amount);
    }
}
