package com.company.library.dao;

import com.company.library.entity.Identifiable;
import com.company.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * This interface is concerned with performing CRUD operations on the database
 *
 * @param <T> class, with instances of which the DAO operates. Has to be identifiable because all artifact tables contain the id column
 */

public interface Dao<T extends Identifiable> {

    /**
     * Gets the object of id from database
     *
     * @param id id of object in the database
     * @return Optional of object if there exists object of id in the database. Otherwise, returns Optional.empty()
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    Optional<T> getById(Long id) throws DaoException;

    /**
     * Gets all objects from a database
     *
     * @return List object containing all found objects. May be empty
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    List<T> getAll() throws DaoException;

    /**
     * Adds an Object to the database or, if there already exists an object with the same id, updates the existing objects with new fields
     *
     * @param item object to be saved to the database
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void save(T item) throws DaoException;

    /**
     * Deletes object of id from the database
     *
     * @param id id of object in the database to be deleted
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void removeById(Long id) throws DaoException;

    /**
     * Gets id of object with the same state. If there is no such object in the database, then this method creates one
     *
     * @param object object to search for equals of in the database or to save to the database and get id of
     * @return Long object representing the id value of found/created object
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    Long getIdOfNewOrExistingObject(T object) throws DaoException;
}
