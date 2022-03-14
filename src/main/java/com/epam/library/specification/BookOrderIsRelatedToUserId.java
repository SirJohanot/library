package com.epam.library.specification;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.User;

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
