package com.epam.library.specification;

public abstract class AbstractChainedSpecification<T> implements Specification<T> {

    private final Specification<T> successor;

    public AbstractChainedSpecification() {
        successor = null;
    }

    public AbstractChainedSpecification(Specification<T> successor) {
        this.successor = successor;
    }

    @Override
    public boolean isSpecified(T object) {
        if (!isSpecifiedByTheCurrentSpecification(object)) {
            return false;
        }
        if (successor == null) {
            return true;
        }
        return successor.isSpecified(object);
    }

    protected abstract boolean isSpecifiedByTheCurrentSpecification(T object);
}
