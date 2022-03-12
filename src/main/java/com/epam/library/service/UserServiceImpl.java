package com.epam.library.service;

import com.epam.library.command.validation.UserValidator;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;

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
            if (user.isEmpty()) {
                throw new ServiceException("The requested user does not exist");
            }

            helper.endTransaction();
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveUser(Long id, String login, String name, String surname, UserRole role, boolean blocked, UserValidator userValidator) throws ServiceException {
        User user = new User(id, login, name, surname, role, blocked);
        try {
            userValidator.validate(user);
        } catch (ValidationException e) {
            throw new ServiceException(e);
        }
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            UserDao dao = helper.createUserDao();
            dao.save(user);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setUserBlockStatus(Long id, Boolean newValue) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            UserDao dao = helper.createUserDao();
            dao.updateUserBlocked(id, newValue);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}