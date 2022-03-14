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
import com.epam.library.specification.NoSpecification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private DaoHelperFactory helperFactory;
    private DaoHelper helper;
    private UserDao userDao;

    private final Long userId = 1L;
    private final String login = "login";
    private final String name = "name";
    private final String surname = "surname";
    private final UserRole role = UserRole.LIBRARIAN;
    private final boolean blocked = false;

    private UserServiceImpl userService;

    @Before
    public void setUp() throws DaoException {
        userDao = mock(UserDao.class);
        helper = mock(DaoHelper.class);
        when(helper.createUserDao()).thenReturn(userDao);
        helperFactory = mock(DaoHelperFactory.class);
        when(helperFactory.createHelper()).thenReturn(helper);
        userService = new UserServiceImpl(helperFactory);
    }

    @After
    public void tearDown() {
        userDao = null;
        helper = null;
        helperFactory = null;
        userService = null;
    }

    @Test
    public void testSignInShouldDelegateToUserDaoCreatedByHelper() throws DaoException, ServiceException {
        //given
        String password = "Password";
        User expectedUser = new User(userId, login, name, surname, role, blocked);
        Optional<User> expectedUserOptional = Optional.of(expectedUser);
        when(userDao.findUserByLoginAndPassword(login, password)).thenReturn(expectedUserOptional);
        //when
        Optional<User> actualUserOptional = userService.signIn(login, password);
        //then
        Assert.assertEquals(expectedUserOptional, actualUserOptional);
    }

    @Test
    public void testGetAllSpecifiedUsersShouldDelegateToUserDaoCreatedByHelperWhenThereIsNoSpecification() throws DaoException, ServiceException {
        //given
        User expectedUser = new User(userId, login, name, surname, role, blocked);
        List<User> expectedUserList = List.of(expectedUser);
        when(userDao.getAll()).thenReturn(expectedUserList);
        //when
        List<User> actualUserList = userService.getAllSpecifiedUsers(new NoSpecification<>());
        //then
        Assert.assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void testGetUserByIdShouldReturnTheUserWhenThereIsSuchUser() throws DaoException, ServiceException {
        //given
        User expectedUser = new User(userId, login, name, surname, role, blocked);
        Optional<User> userOptionalReturnedByDao = Optional.of(expectedUser);
        when(userDao.getById(userId)).thenReturn(userOptionalReturnedByDao);
        //when
        User actualUser = userService.getUserById(userId);
        //then
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test(expected = ServiceException.class)
    public void testGetUserByIdShouldThrowAnExceptionWhenDaoCouldNotFindTheUser() throws DaoException, ServiceException {
        //given
        Optional<User> userOptionalReturnedByDao = Optional.empty();
        when(userDao.getById(userId)).thenReturn(userOptionalReturnedByDao);
        //when
        User actualUser = userService.getUserById(userId);
        //then
    }

    @Test
    public void testSaveUserShouldInvokeSaveMethodOfDaoWhenUserIsValid() throws DaoException, ServiceException, ValidationException {
        //given
        User userToBeSaved = new User(userId, login, name, surname, role, blocked);

        UserValidator userValidator = mock(UserValidator.class);
        doNothing().when(userValidator).validate(userToBeSaved);
        //when
        userService.saveUser(userId, login, name, surname, role, blocked, userValidator);
        //then
        verify(userDao, times(1)).save(userToBeSaved);
    }

    @Test(expected = ServiceException.class)
    public void testSaveUserShouldThrowServiceExceptionWhenUserIsNotValid() throws ServiceException, ValidationException {
        //given
        User userToBeSaved = new User(userId, login, name, surname, role, blocked);

        UserValidator userValidator = mock(UserValidator.class);
        doThrow(new ValidationException()).when(userValidator).validate(userToBeSaved);
        //when
        userService.saveUser(userId, login, name, surname, role, blocked, userValidator);
        //then
    }

    @Test
    public void testSetUserBlockedStatusShouldInvokeUpdateUserBlockedMethodOfDao() throws DaoException, ServiceException {
        //given
        //when
        userService.setUserBlockStatus(userId, blocked);
        //then
        verify(userDao, times(1)).updateUserBlocked(userId, blocked);
    }
}
