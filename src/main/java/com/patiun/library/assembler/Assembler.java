package com.patiun.library.assembler;

import com.patiun.library.dao.Dao;
import com.patiun.library.entity.Identifiable;

/**
 * Assemblers are concerned with using multiple DAO's to properly manipulate the information in the database
 *
 * @param <T> type of object
 */
public interface Assembler<T extends Identifiable> extends Dao<T> {
}
