package com.company.library.specification;

import com.company.library.entity.User;
import com.company.library.entity.enumeration.UserRole;

public class UserContainsLineSpecification extends AbstractChainedContainsLineSpecification<User> {

    public UserContainsLineSpecification(String targetLine) {
        super(targetLine);
    }

    public UserContainsLineSpecification(Specification<User> successor, String targetLine) {
        super(successor, targetLine);
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(User object) {
        String login = object.getLogin();
        if (containsTargetLineIgnoreCase(login)) {
            return true;
        }

        String name = object.getName();
        if (containsTargetLineIgnoreCase(name)) {
            return true;
        }

        String surname = object.getSurname();
        if (containsTargetLineIgnoreCase(surname)) {
            return true;
        }

        UserRole role = object.getRole();
        String roleLine = role.toString();
        if (containsTargetLineIgnoreCase(roleLine)) {
            return true;
        }

        boolean blocked = object.isBlocked();
        String blockedLine = Boolean.toString(blocked);
        return containsTargetLineIgnoreCase(blockedLine);
    }
}
