package com.company.library.dao;

import com.company.library.entity.Identifiable;
import com.company.library.exception.DaoException;

/**
 * This interface is concerned with deleting objects from the database's tables, which are not referenced by any objects of other database table
 *
 * @param <T> class of object
 */
public interface SecondaryTableDao<T extends Identifiable> extends Dao<T> {

    /**
     * Deletes all objects from the related database table, which are not referenced by any object of another table or do not reference an existing object of another table
     *
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void deleteRedundant() throws DaoException;

}
