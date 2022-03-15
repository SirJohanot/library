package com.epam.library.repository;

import com.epam.library.dao.Dao;
import com.epam.library.entity.Identifiable;

/**
 * Repositories are concerned with using multiple DAO's to properly manipulate the information in the database
 *
 * @param <T> type of object
 */
public interface Repository<T extends Identifiable> extends Dao<T> {
}
