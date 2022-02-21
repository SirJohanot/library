package com.epam.library.dao;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable> {

    Optional<T> getById(Long id) throws DaoException;

    List<T> getAll() throws DaoException;

    void save(T item) throws DaoException;

    void removeById(Long id) throws DaoException;
}
