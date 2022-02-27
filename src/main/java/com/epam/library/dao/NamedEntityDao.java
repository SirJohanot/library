package com.epam.library.dao;

import com.epam.library.entity.Named;
import com.epam.library.exception.DaoException;

import java.util.Optional;

public interface NamedEntityDao<T extends Named> {

    Optional<T> getByName(String name) throws DaoException;

}
