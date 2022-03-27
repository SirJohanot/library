package com.epam.library.mapper;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookOrderRowMapper implements RowMapper<BookOrder> {

    @Override
    public BookOrder map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(BookOrder.ID_COLUMN);

        Long bookId = resultSet.getLong(BookOrder.BOOK_ID_COLUMN);
        Book book = Book.ofId(bookId);

        Long userId = resultSet.getLong(BookOrder.USER_ID_COLUMN);
        User user = User.ofId(userId);

        Date startDate = resultSet.getDate(BookOrder.START_DATE_COLUMN);
        Date endDate = resultSet.getDate(BookOrder.END_DATE_COLUMN);
        Date returnDate = resultSet.getDate(BookOrder.RETURN_DATE_COLUMN);

        String rentalTypeLowercaseLine = resultSet.getString(BookOrder.RENTAL_TYPE_COLUMN);
        String rentalTypeUppercaseLine = rentalTypeLowercaseLine.toUpperCase();
        RentalType rentalType = RentalType.valueOf(rentalTypeUppercaseLine);

        String rentalStateLowercaseLine = resultSet.getString(BookOrder.RENTAL_STATE_COLUMN);
        String rentalStateUppercaseLine = rentalStateLowercaseLine.toUpperCase();
        RentalState rentalState = RentalState.valueOf(rentalStateUppercaseLine);

        return new BookOrder(id, book, user, startDate, endDate, returnDate, rentalType, rentalState);
    }
}
