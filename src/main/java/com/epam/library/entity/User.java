package com.epam.library.entity;

import com.epam.library.entity.enumeration.UserRole;

import java.io.Serializable;

public class User implements Identifiable, Serializable {

    public static final String TABLE_NAME = "user";
    public static final String ID_COLUMN = "id";
    public static final String LOGIN_COLUMN = "login";
    public static final String NAME_COLUMN = "name";
    public static final String SURNAME_COLUMN = "surname";
    public static final String ROLE_COLUMN = "role";
    public static final String BLOCKED_COLUMN = "is_blocked";


    private final Long id;
    private final String login;
    private final String name;
    private final String surname;
    private final UserRole role;
    private final boolean blocked;

    public User(Long id, String login, String name, String surname, UserRole role, boolean blocked) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.blocked = blocked;
    }

    public static User ofId(Long id) {
        return new User(id, null, null, null, null, false);
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

    public boolean isBlocked() {
        return blocked;
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

        if (blocked != user.blocked) {
            return false;
        }
        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
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
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }
}
