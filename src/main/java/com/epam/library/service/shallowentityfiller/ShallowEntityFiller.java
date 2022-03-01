package com.epam.library.service.shallowentityfiller;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;

public interface ShallowEntityFiller<T extends Identifiable> {

    T fillShallowEntity(T shallowEntity) throws DaoException;
}
