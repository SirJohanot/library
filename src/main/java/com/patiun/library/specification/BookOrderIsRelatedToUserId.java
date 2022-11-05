package com.patiun.library.specification;

import com.patiun.library.entity.User;
import com.patiun.library.entity.BookOrder;

public class BookOrderIsRelatedToUserId extends AbstractChainedSpecification<BookOrder> {

    private final Long userId;

    public BookOrderIsRelatedToUserId(Long userId) {
        this.userId = userId;
    }

    public BookOrderIsRelatedToUserId(Specification<BookOrder> successor, Long userId) {
        super(successor);
        this.userId = userId;
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(BookOrder object) {
        User bookOrderUser = object.getUser();
        Long bookOrderUserId = bookOrderUser.getId();

        return userId.equals(bookOrderUserId);
    }
}
