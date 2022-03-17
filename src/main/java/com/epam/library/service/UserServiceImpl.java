package com.epam.library.service;

import com.epam.library.dao.UserDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.specification.Specification;
import com.epam.library.validation.UserValidator;
import com.epam.library.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

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
    public void signUp(String login, String password, String name, String surname, Validator<User> userValidator, Validator<String> passwordValidator) throws ServiceException, ValidationException {
        User user = new User(null, login, name, surname, UserRole.READER, false);
        passwordValidator.validate(password);
        userValidator.validate(user);
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            UserDao dao = helper.createUserDao();
            Optional<User> sameLoginUserOptional = dao.findUserByLogin(login);
            if (sameLoginUserOptional.isPresent()) {
                throw new ValidationException("A User by such login already exists");
            }
            dao.saveWithPassword(user, password);

            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getSpecifiedUsers(Specification<User> userSpecification) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            UserDao dao = helper.createUserDao();
            List<User> allUsers = dao.getAll();

            List<User> specifiedUsers = new ArrayList<>();
            for (User user : allUsers) {
                if (userSpecification.isSpecified(user)) {
                    specifiedUsers.add(user);
                }
            }

            helper.endTransaction();
            return specifiedUsers;
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
                ServiceException serviceException = new ServiceException("The requested user does not exist");
                LOGGER.error("User Id: " + id, serviceException);
                throw serviceException;
            }

            helper.endTransaction();
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editUser(Long id, String login, String name, String surname, UserRole role, boolean blocked, UserValidator userValidator) throws ServiceException, ValidationException {
        if (id == null) {
            ServiceException serviceException = new ServiceException("Cannot edit user by null id");
            LOGGER.error(serviceException);
            throw serviceException;
        }
        User user = new User(id, login, name, surname, role, blocked);
        userValidator.validate(user);
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