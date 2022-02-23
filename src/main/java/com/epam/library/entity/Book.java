package com.epam.library.entity;

import java.time.Year;

public class Book implements Identifiable {

    private final Long id;
    private final String title;
    private final Long genreId;
    private final Long publisherId;
    private final Year publishmentYear;
    private final int amount;

    public Book(Long id, String title, Long genreId, Long publisherId, Year publishmentYear, int amount) {
        this.id = id;
        this.title = title;
        this.genreId = genreId;
        this.publisherId = publisherId;
        this.publishmentYear = publishmentYear;
        this.amount = amount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Year getPublishmentYear() {
        return publishmentYear;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        if (amount != book.amount) {
            return false;
        }
        if (id != null ? !id.equals(book.id) : book.id != null) {
            return false;
        }
        if (title != null ? !title.equals(book.title) : book.title != null) {
            return false;
        }
        if (genreId != null ? !genreId.equals(book.genreId) : book.genreId != null) {
            return false;
        }
        if (publisherId != null ? !publisherId.equals(book.publisherId) : book.publisherId != null) {
            return false;
        }
        return publishmentYear != null ? publishmentYear.equals(book.publishmentYear) : book.publishmentYear == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        result = 31 * result + (publisherId != null ? publisherId.hashCode() : 0);
        result = 31 * result + (publishmentYear != null ? publishmentYear.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genreId=" + genreId +
                ", publisherId=" + publisherId +
                ", publishmentYear=" + publishmentYear +
                ", amount=" + amount +
                '}';
    }
}
