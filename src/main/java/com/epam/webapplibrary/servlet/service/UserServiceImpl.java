package com.epam.webapplibrary.servlet.service;

public class UserServiceImpl implements UserService {


    @Override
    public boolean login(String login, String password) {
        return "admin".equals(login) && "admin".equals(password);
    }
}
