package com.epam.library.specification;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;

public class UserContainsLineSpecification extends AbstractChainedSpecification<User> {

    private final String lineToContain;

    public UserContainsLineSpecification(String lineToContain) {
        this.lineToContain = lineToContain;
    }

    public UserContainsLineSpecification(String lineToContain, Specification<User> successor) {
        super(successor);
        this.lineToContain = lineToContain;
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(User object) {
        String login = object.getLogin();
        if (login.contains(lineToContain)) {
            return true;
        }

        String name = object.getName();
        if (name.contains(lineToContain)) {
            return true;
        }

        String surname = object.getSurname();
        if (surname.contains(lineToContain)) {
            return true;
        }

        UserRole role = object.getRole();
        String roleLine = role.toString();
        if (roleLine.contains(lineToContain)) {
            return true;
        }

        boolean blocked = object.isBlocked();
        String blockedLine = Boolean.toString(blocked);
        return blockedLine.contains(lineToContain);
    }
}
