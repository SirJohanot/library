package com.patiun.library.mapper;

import com.patiun.library.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is concerned with creating objects from ResultSets got from executing SQL scripts
 *
 * @param <T> class of object to be created
 */
public interface RowMapper<T extends Identifiable> {

    /**
     * Creates an object from a ResultSet
     *
     * @param resultSet ResultSet object, which contains the row representing the object in the database
     * @return the created object
     * @throws SQLException if the resultSet does not contain a column needed to create the object
     */
    T map(ResultSet resultSet) throws SQLException;

}
