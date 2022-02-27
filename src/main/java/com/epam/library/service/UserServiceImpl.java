package com.epam.library.service;

import com.epam.library.dao.UserDao;
import com.epam.library.dao.daohelper.DaoHelper;
import com.epam.library.dao.daohelper.DaoHelperFactory;
import com.epam.library.entity.User;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final DaoHelperFactory daoHelperFactory;

    public UserServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            UserDao dao = helper.createUserDao();
            Optional<User> user = dao.findUserByLoginAndPassword(login, password);
            helper.endTransaction();
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            UserDao dao = helper.createUserDao();
            List<User> userList = dao.getAll();
            helper.endTransaction();
            return userList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            UserDao dao = helper.createUserDao();
            Optional<User> user = dao.getById(id);
            helper.endTransaction();
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}