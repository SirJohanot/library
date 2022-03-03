package com.epam.library.dao;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;

public interface SecondaryTableDao<T extends Identifiable> extends Dao<T> {

    void deleteUnreferenced(String primaryTableName, String primaryTableColumnName) throws DaoException;

}
