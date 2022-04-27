package com.company.library.assembler;

import com.company.library.dao.Dao;
import com.company.library.entity.Identifiable;

/**
 * Repositories are concerned with using multiple DAO's to properly manipulate the information in the database
 *
 * @param <T> type of object
 */
public interface Assembler<T extends Identifiable> extends Dao<T> {
}
