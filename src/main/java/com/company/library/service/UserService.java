package com.company.library.service;

import com.company.library.entity.User;
import com.company.library.entity.enumeration.UserRole;
import com.company.library.exception.ServiceException;
import com.company.library.exception.ValidationException;
import com.company.library.specification.Specification;

import java.util.List;
import java.util.Optional;

/**
 * This interface is concerned with manipulating Users in the database and performing other User-related logic
 */

public interface UserService {

    /**
     * Searches for a User in the database with the same login and password
     *
     * @param login    String representing the User's login
     * @param password String representing the User's password
     * @return Optional.empty() if no such User was found. Otherwise, Optional of found User
     * @throws ServiceException if a DaoException occurs
     */
    Optional<User> signIn(String login, String password) throws ServiceException;

    /**
     * Saves the User along with their password using DAO
     *
     * @param login    User's login
     * @param password the password
     * @param name     User's name
     * @param surname  User's surname
     * @throws ServiceException    if a DaoException occurs
     * @throws ValidationException if the User or the password is not valid
     */
    void signUp(String login, String password, String name, String surname) throws ServiceException, ValidationException;

    /**
     * Gets all Users from the database that fit the passed specification
     *
     * @param userSpecification specification that all returned objects must fit
     * @return List object containing specified Users
     * @throws ServiceException - if a DaoException occurs
     */
    List<User> getSpecifiedUsers(Specification<User> userSpecification) throws ServiceException;

    /**
     * Gets User by id in the database
     *
     * @param id Long representing the User's id
     * @return User object from the database
     * @throws ServiceException if User by such id does not exist in the database or if a DaoException occurs
     */
    User getUserById(Long id) throws ServiceException;

    /**
     * Inserts or updates the User in the database
     *
     * @param id      id of User. If null, the book is inserted into the database. If not null, the Book is updated in the database
     * @param login   login of User
     * @param name    name of User
     * @param surname surname of User
     * @param role    role of User
     * @param blocked blocked state of User
     * @throws ServiceException    if a DaoException occurs
     * @throws ValidationException if the User is not valid
     */
    void editUser(Long id, String login, String name, String surname, UserRole role, boolean blocked) throws ServiceException, ValidationException;

    /**
     * Updates the User's blocked status
     *
     * @param id       id of User in the database
     * @param newValue new blocked state to change to
     * @throws ServiceException if a DaoException occurs
     */
    void setUserBlockStatus(Long id, Boolean newValue) throws ServiceException;
}
