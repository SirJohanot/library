package com.epam.library.service.shallowentityfiller;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

/**
 * This interface is concerned with using DAO objects (or possibly other ShallowEntityFillers) to get all objects related to inputted object from the database
 *
 * @param <T> class of object
 */
public interface ShallowEntityFiller<T extends Identifiable> {

    /**
     * Gets objects by foreign keys of shallowEntity and returns the full representation of the object in the database
     *
     * @param shallowEntity object to fill
     * @return the object, along with all objects related to it
     * @throws DaoException     if a DaoException occurs while executing a method of a DAO object
     * @throws ServiceException if one of the objects related to shallowEntity could not be found
     */
    T fillShallowEntity(T shallowEntity) throws DaoException, ServiceException;
}
