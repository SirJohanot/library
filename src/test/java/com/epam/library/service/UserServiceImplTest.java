package com.epam.library.service;

import com.epam.library.dao.UserDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.specification.UserContainsLineSpecification;
import com.epam.library.validation.PasswordValidator;
import com.epam.library.validation.UserValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
    public void testSignUpShouldInvokeSaveWithPasswordMethodOfUserDaoWhenTheUserAndThePasswordAreValidAndAUserWithTheSameLoginDoesNotExist() throws DaoException, ValidationException, ServiceException {
        //given
        String password = "Password";

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        doNothing().when(passwordValidator).validate(password);

        User expectedUserToGetSaved = new User(null, login, name, surname, UserRole.READER, false);
        UserValidator userValidator = mock(UserValidator.class);
        doNothing().when(userValidator).validate(expectedUserToGetSaved);

        Optional<User> expectedFoundSameLoginUserOptional = Optional.empty();
        when(userDao.findUserByLogin(login)).thenReturn(expectedFoundSameLoginUserOptional);
        //when
        userService.signUp(login, password, name, surname, userValidator, passwordValidator);
        //then
        verify(userDao, times(1)).saveWithPassword(expectedUserToGetSaved, password);
    }

    @Test(expected = ValidationException.class)
    public void testSignUpShouldThrowValidationExceptionWhenTheUserIsNotValid() throws DaoException, ValidationException, ServiceException {
        //given
        String password = "Password";

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        doNothing().when(passwordValidator).validate(password);

        User expectedUserToGetSaved = new User(null, login, name, surname, UserRole.READER, false);
        UserValidator userValidator = mock(UserValidator.class);
        doThrow(ValidationException.class).when(userValidator).validate(expectedUserToGetSaved);

        Optional<User> expectedFoundSameLoginUserOptional = Optional.empty();
        when(userDao.findUserByLogin(login)).thenReturn(expectedFoundSameLoginUserOptional);
        //when
        userService.signUp(login, password, name, surname, userValidator, passwordValidator);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testSignUpShouldThrowValidationExceptionWhenThePasswordIsNotValid() throws DaoException, ValidationException, ServiceException {
        //given
        String password = "Password";

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        doThrow(ValidationException.class).when(passwordValidator).validate(password);

        User expectedUserToGetSaved = new User(null, login, name, surname, UserRole.READER, false);
        UserValidator userValidator = mock(UserValidator.class);
        doNothing().when(userValidator).validate(expectedUserToGetSaved);

        Optional<User> expectedFoundSameLoginUserOptional = Optional.empty();
        when(userDao.findUserByLogin(login)).thenReturn(expectedFoundSameLoginUserOptional);
        //when
        userService.signUp(login, password, name, surname, userValidator, passwordValidator);
        //then
    }

    @Test(expected = ValidationException.class)
    public void testSignUpShouldThrowValidationExceptionWhenAUserWithSameLoginAlreadyExists() throws DaoException, ValidationException, ServiceException {
        //given
        String password = "Password";

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        doNothing().when(passwordValidator).validate(password);

        User expectedUserToGetSaved = new User(null, login, name, surname, UserRole.READER, false);
        UserValidator userValidator = mock(UserValidator.class);
        doNothing().when(userValidator).validate(expectedUserToGetSaved);

        Optional<User> expectedFoundSameLoginUserOptional = Optional.of(expectedUserToGetSaved);
        when(userDao.findUserByLogin(login)).thenReturn(expectedFoundSameLoginUserOptional);
        //when
        userService.signUp(login, password, name, surname, userValidator, passwordValidator);
        //then
    }

    @Test
    public void testGetSpecifiedUsersShouldOnlyReturnUsersThatFitTheSpecification() throws DaoException, ServiceException {
        //given
        User firstUser = new User(userId, login, name, surname, role, blocked);

        User secondUser = new User(10L, login, name, surname, role, blocked);

        User thirdUser = new User(15L, login, name, surname, role, blocked);

        UserContainsLineSpecification userSpecification = mock(UserContainsLineSpecification.class);
        when(userSpecification.isSpecified(firstUser)).thenReturn(false);
        when(userSpecification.isSpecified(secondUser)).thenReturn(true);
        when(userSpecification.isSpecified(thirdUser)).thenReturn(false);

        List<User> expectedUserList = List.of(secondUser);

        List<User> allUserList = Arrays.asList(firstUser, secondUser, thirdUser);
        when(userDao.getAll()).thenReturn(allUserList);
        //when
        List<User> actualUserList = userService.getSpecifiedUsers(userSpecification);
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
    public void testEditUserShouldInvokeSaveMethodOfDaoWhenUserIsValid() throws DaoException, ServiceException, ValidationException {
        //given
        User userToBeSaved = new User(userId, login, name, surname, role, blocked);

        UserValidator userValidator = mock(UserValidator.class);
        doNothing().when(userValidator).validate(userToBeSaved);
        //when
        userService.editUser(userId, login, name, surname, role, blocked, userValidator);
        //then
        verify(userDao, times(1)).save(userToBeSaved);
    }

    @Test(expected = ValidationException.class)
    public void testEditUserShouldThrowValidationExceptionWhenUserIsNotValid() throws ServiceException, ValidationException {
        //given
        User userToBeSaved = new User(userId, login, name, surname, role, blocked);

        UserValidator userValidator = mock(UserValidator.class);
        doThrow(ValidationException.class).when(userValidator).validate(userToBeSaved);
        //when
        userService.editUser(userId, login, name, surname, role, blocked, userValidator);
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
