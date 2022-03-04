package com.epam.library.dao;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;

/**
 * This interface is concerned with deleting objects from the database's tables, which are not referenced by any objects of other database table
 *
 * @param <T> class of object
 */
public interface SecondaryTableDao<T extends Identifiable> extends Dao<T> {

    /**
     * Deletes all objects from the related database table, which are not referenced by any objects of primaryTable
     *
     * @param primaryTableName       name of the table, which the deleted objects are not referenced by
     * @param primaryTableColumnName name of column of primaryTable, which contains the ids of objects not to be deleted
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void deleteUnreferenced(String primaryTableName, String primaryTableColumnName) throws DaoException;

}
