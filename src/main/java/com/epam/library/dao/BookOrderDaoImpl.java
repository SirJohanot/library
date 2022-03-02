package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.BookOrderRowMapper;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

public class BookOrderDaoImpl extends AbstractDao<BookOrder> implements BookOrderDao {

    private static final String FORMAT_OF_DATES_IN_DATABASE = "yyyy-MM-dd";

    private final SimpleDateFormat sqlDateFormatter = new SimpleDateFormat(FORMAT_OF_DATES_IN_DATABASE);

    private static final String SET_NEW_FIELD_VALUE_QUERY = "UPDATE %s SET %s = ? WHERE id = ? ;";

    public BookOrderDaoImpl(Connection connection) {
        super(connection, new BookOrderRowMapper(), BookOrder.TABLE_NAME);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(BookOrder entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(BookOrder.ID_COLUMN, entity.getId());
        valuesMap.put(BookOrder.BOOK_ID_COLUMN, entity.getBook().getId());
        valuesMap.put(BookOrder.USER_ID_COLUMN, entity.getUser().getId());
        String startDateLine = sqlDateFormatter.format(entity.getStartDate());
        valuesMap.put(BookOrder.START_DATE_COLUMN, startDateLine);
        String endDateLine = sqlDateFormatter.format(entity.getEndDate());
        valuesMap.put(BookOrder.END_DATE_COLUMN, endDateLine);
        String returnDateLine = sqlDateFormatter.format(entity.getReturnDate());
        valuesMap.put(BookOrder.RETURN_DATE_COLUMN, returnDateLine);
        valuesMap.put(BookOrder.RENTAL_TYPE_COLUMN, entity.getType());
        valuesMap.put(BookOrder.RENTAL_STATE_COLUMN, entity.getState());
        return valuesMap;
    }

    @Override
    public void setState(Long id, RentalState newState) throws DaoException {
        String query = String.format(SET_NEW_FIELD_VALUE_QUERY, BookOrder.TABLE_NAME, BookOrder.RENTAL_STATE_COLUMN);
        executeUpdate(query, newState, id);
    }

    @Override
    public void setReturnDate(Long id, Date returnDate) throws DaoException {
        String query = String.format(SET_NEW_FIELD_VALUE_QUERY, BookOrder.TABLE_NAME, BookOrder.RETURN_DATE_COLUMN);
        String returnDateLine = sqlDateFormatter.format(returnDate);
        executeUpdate(query, returnDateLine, id);
    }
}
