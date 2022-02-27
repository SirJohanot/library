package com.epam.library.service;

import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> signIn(String login, String password) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    User getUserById(Long id) throws ServiceException;
}
