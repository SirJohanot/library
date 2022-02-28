package com.epam.library.service;

import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> signIn(String login, String password) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    User getUserById(String idLine) throws ServiceException;

    void saveUser(String idLine, String login, String name, String surname, String roleLine, String blockedLine) throws ServiceException;

    void setUserBlockStatus(String idLine, boolean newValue) throws ServiceException;
}
