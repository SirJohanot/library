package com.epam.library.mapper;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BookOrderRowMapper implements RowMapper<BookOrder> {

    @Override
    public BookOrder map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(BookOrder.ID_COLUMN);
        Long bookId = resultSet.getLong(BookOrder.BOOK_ID_COLUMN);
        Long userId = resultSet.getLong(BookOrder.USER_ID_COLUMN);
        Date startDate = resultSet.getDate(BookOrder.START_DATE_COLUMN);
        Date endDate = resultSet.getDate(BookOrder.END_DATE_COLUMN);
        Date returnDate = resultSet.getDate(BookOrder.RETURN_DATE_COLUMN);
        RentalType rentalType = RentalType.valueOf(resultSet.getString(BookOrder.RENTAL_TYPE_COLUMN).toUpperCase());
        RentalState rentalState = RentalState.valueOf(resultSet.getString(BookOrder.RENTAL_STATE_COLUMN).toUpperCase());
        return new BookOrder(id, bookId, userId, startDate, endDate, returnDate, rentalType, rentalState);
    }
}
