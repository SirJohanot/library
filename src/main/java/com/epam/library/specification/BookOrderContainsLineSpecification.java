package com.epam.library.specification;

import com.epam.library.entity.BookOrder;
import com.epam.library.entity.enumeration.RentalState;
import com.epam.library.entity.enumeration.RentalType;

import java.sql.Date;

public class BookOrderContainsLineSpecification extends AbstractChainedSpecification<BookOrder> {

    private final String lineToContain;

    public BookOrderContainsLineSpecification(String lineToContain) {
        this.lineToContain = lineToContain;
    }

    public BookOrderContainsLineSpecification(Specification<BookOrder> successor, String lineToContain) {
        super(successor);
        this.lineToContain = lineToContain;
    }

    @Override
    protected boolean isSpecifiedByTheCurrentSpecification(BookOrder object) {
        Date startDate = object.getStartDate();
        String startDateLine = startDate.toString();
        if (startDateLine.contains(lineToContain)) {
            return true;
        }

        Date endDate = object.getEndDate();
        String endDateLine = endDate.toString();
        if (endDateLine.contains(lineToContain)) {
            return true;
        }

        Date returnDate = object.getReturnDate();
        if (returnDate != null) {
            String returnDateLine = returnDate.toString();
            if (returnDateLine.contains(lineToContain)) {
                return true;
            }
        }

        RentalType type = object.getType();
        String typeLine = type.toString();
        if (typeLine.contains(lineToContain)) {
            return true;
        }

        RentalState state = object.getState();
        String stateLine = state.toString();
        return stateLine.contains(lineToContain);
    }
}
