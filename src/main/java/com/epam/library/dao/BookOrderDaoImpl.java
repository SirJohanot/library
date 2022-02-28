package com.epam.library.dao;

import com.epam.library.entity.BookOrder;
import com.epam.library.mapper.BookOrderRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;

public class BookOrderDaoImpl extends AbstractDao<BookOrder> implements BookOrderDao {

    public BookOrderDaoImpl(Connection connection) {
        super(connection, new BookOrderRowMapper(), BookOrder.TABLE_NAME);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(BookOrder entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(BookOrder.ID_COLUMN, entity.getId());
        valuesMap.put(BookOrder.BOOK_ID_COLUMN, entity.getBookId());
        valuesMap.put(BookOrder.USER_ID_COLUMN, entity.getUserId());
        valuesMap.put(BookOrder.START_DATE_COLUMN, entity.getStartDate());
        valuesMap.put(BookOrder.END_DATE_COLUMN, entity.getEndDate());
        valuesMap.put(BookOrder.RETURN_DATE_COLUMN, entity.getReturnDate());
        valuesMap.put(BookOrder.RENTAL_TYPE_COLUMN, entity.getType());
        valuesMap.put(BookOrder.RENTAL_STATE_COLUMN, entity.getState());
        return valuesMap;
    }
}
