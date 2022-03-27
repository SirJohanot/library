package com.epam.library.entity.book;

import com.epam.library.entity.Identifiable;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

public class Book implements Identifiable, Serializable {

    public static final String TABLE_NAME = "book";
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String GENRE_ID_COLUMN = "genre_id";
    public static final String PUBLISHER_ID_COLUMN = "publisher_id";
    public static final String PUBLISHMENT_YEAR_COLUMN = "publishment_year";
    public static final String AMOUNT_COLUMN = "amount";


    private final Long id;
    private final String title;
    private final List<Author> authorList;
    private final Genre genre;
    private final Publisher publisher;
    private final Year publishmentYear;
    private final Integer amount;

    public Book(Long id, String title, List<Author> authorList, Genre genre, Publisher publisher, Year publishmentYear, Integer amount) {
        this.id = id;
        this.title = title;
        this.authorList = authorList;
        this.genre = genre;
        this.publisher = publisher;
        this.publishmentYear = publishmentYear;
        this.amount = amount;
    }

    public static Book ofId(Long id) {
        return new Book(id, null, null, null, null, null, 0);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public Genre getGenre() {
        return genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Year getPublishmentYear() {
        return publishmentYear;
    }

    public Integer getAmount() {
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
        if (id != null ? !id.equals(book.id) : book.id != null) {
            return false;
        }
        if (title != null ? !title.equals(book.title) : book.title != null) {
            return false;
        }
        if (authorList != null ? !authorList.equals(book.authorList) : book.authorList != null) {
            return false;
        }
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) {
            return false;
        }
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) {
            return false;
        }
        if (publishmentYear != null ? !publishmentYear.equals(book.publishmentYear) : book.publishmentYear != null) {
            return false;
        }
        return amount != null ? amount.equals(book.amount) : book.amount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (authorList != null ? authorList.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (publishmentYear != null ? publishmentYear.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorList=" + authorList +
                ", genre=" + genre +
                ", publisher=" + publisher +
                ", publishmentYear=" + publishmentYear +
                ", amount=" + amount +
                '}';
    }
}
