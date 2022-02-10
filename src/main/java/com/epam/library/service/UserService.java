package com.epam.library.service;

import com.epam.library.entity.User;
import com.epam.library.exception.ServiceException;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String login, String password) throws ServiceException;
}
