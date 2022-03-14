package com.epam.library.specification;

public class NoSpecification<T> implements Specification<T> {

    @Override
    public boolean isSpecified(T object) {
        return true;
    }
}
