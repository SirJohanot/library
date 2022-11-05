package com.patiun.library.entity;

import com.patiun.library.entity.book.Book;
import com.patiun.library.entity.enumeration.RentalState;
import com.patiun.library.entity.enumeration.RentalType;

import java.io.Serializable;
import java.sql.Date;

public class BookOrder implements Identifiable, Serializable {

    public static final String TABLE_NAME = "book_order";
    public static final String ID_COLUMN = "id";
    public static final String BOOK_ID_COLUMN = "book_id";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String START_DATE_COLUMN = "start_date";
    public static final String END_DATE_COLUMN = "end_date";
    public static final String RETURN_DATE_COLUMN = "return_date";
    public static final String RENTAL_TYPE_COLUMN = "rental_type";
    public static final String RENTAL_STATE_COLUMN = "state";

    private final Long id;
    private final Book book;
    private final User user;
    private final Date startDate;
    private final Date endDate;
    private final Date returnDate;
    private final RentalType type;
    private final RentalState state;

    public BookOrder(Long id, Book book, User user, Date startDate, Date endDate, Date returnDate, RentalType type, RentalState state) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
        this.type = type;
        this.state = state;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public RentalType getType() {
        return type;
    }

    public RentalState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookOrder bookOrder = (BookOrder) o;
        if (id != null ? !id.equals(bookOrder.id) : bookOrder.id != null) {
            return false;
        }
        if (book != null ? !book.equals(bookOrder.book) : bookOrder.book != null) {
            return false;
        }
        if (user != null ? !user.equals(bookOrder.user) : bookOrder.user != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(bookOrder.startDate) : bookOrder.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(bookOrder.endDate) : bookOrder.endDate != null) {
            return false;
        }
        if (returnDate != null ? !returnDate.equals(bookOrder.returnDate) : bookOrder.returnDate != null) {
            return false;
        }
        if (type != bookOrder.type) {
            return false;
        }
        return state == bookOrder.state;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookOrder{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", returnDate=" + returnDate +
                ", type=" + type +
                ", state=" + state +
                '}';
    }
}
