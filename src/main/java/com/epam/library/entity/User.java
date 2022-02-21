package com.epam.library.entity;

import java.io.Serializable;

public class User implements Identifiable, Serializable {
    
    public static final String TABLE_NAME = "user";
    public static final String ID_COLUMN = "id";
    public static final String LOGIN_COLUMN = "login";
    public static final String NAME_COLUMN = "name";
    public static final String SURNAME_COLUMN = "surname";
    public static final String ROLE_COLUMN = "role";


    private final Long id;
    private final String login;
    private final String name;
    private final String surname;
    private final UserRole role;

    public User(Long id, String login, String name, String surname, UserRole role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) {
            return false;
        }
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                '}';
    }

}
