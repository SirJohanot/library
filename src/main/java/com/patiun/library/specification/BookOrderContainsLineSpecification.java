package com.patiun.library.specification;

import com.patiun.library.entity.BookOrder;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.entity.enumeration.RentalType;

import java.sql.Date;

public class BookOrderContainsLineSpecification extends AbstractChainedContainsLineSpecification<BookOrder> {

    public BookOrderContainsLineSpecification(String targetLine) {
        super(targetLine);
    }

    public BookOrderContainsLineSpecification(Specification<BookOrder> successor, String targetLine) {
        super(successor, targetLine);
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(BookOrder object) {
        Date startDate = object.getStartDate();
        String startDateLine = startDate.toString();
        if (containsTargetLineIgnoreCase(startDateLine)) {
            return true;
        }

        Date endDate = object.getEndDate();
        String endDateLine = endDate.toString();
        if (containsTargetLineIgnoreCase(endDateLine)) {
            return true;
        }

        Date returnDate = object.getReturnDate();
        if (returnDate != null) {
            String returnDateLine = returnDate.toString();
            if (containsTargetLineIgnoreCase(returnDateLine)) {
                return true;
            }
        }

        RentalType type = object.getType();
        String typeLine = type.toString();
        if (containsTargetLineIgnoreCase(typeLine)) {
            return true;
        }

        RentalState state = object.getState();
        String stateLine = state.toString();
        return containsTargetLineIgnoreCase(stateLine);
    }
}
