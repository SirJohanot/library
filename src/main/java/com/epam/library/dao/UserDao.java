package com.epam.library.dao;

import com.epam.library.entity.User;
import com.epam.library.exception.DaoException;

import java.util.Optional;

/**
 * This interface is concerned with manipulating Users' blocked states and finding their entries in the database by login and password
 */
public interface UserDao extends Dao<User> {

    /**
     * Searches the database for a User with the same login-password pair
     *
     * @param login    String value representing the login to search by
     * @param password String value representing the password to search by
     * @return Optional.empty() if there is no such user. Otherwise, returns Optional object containing found User
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Searches the database for a User with the same login
     *
     * @param login String value representing the login to search by
     * @return Optional.empty() if there is no such user. Otherwise, returns Optional object containing found User
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Saves the user, along with the inputted password
     *
     * @param user     User to be saved
     * @param password the User's password
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void saveWithPassword(User user, String password) throws DaoException;

    /**
     * Sets a new value for a User in the database
     *
     * @param id       Long value representing the id of the User in the database
     * @param newValue boolean value to set is_blocked column value to
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void updateUserBlocked(Long id, boolean newValue) throws DaoException;

}
