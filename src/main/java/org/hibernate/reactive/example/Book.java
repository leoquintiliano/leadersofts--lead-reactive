package org.hibernate.reactive.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="books")
class Book {
    @Id @GeneratedValue
    private Integer id;

    @Size(min=13, max=13)
    private String isbn;

    @NotNull @Size(max=100)
    private String title;

    @Basic(fetch = LAZY)
    @NotNull @Past
    private LocalDate published;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Author author;

    Book(String isbn, String title, Author author, LocalDate published) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.published = published;
    }


    public Book() {

    }

    Integer getId() {
        return id;
    }

    String getIsbn() {
        return isbn;
    }

    String getTitle() {
        return title;
    }

    Author getAuthor() {
        return author;
    }

    LocalDate getPublished() {
        return published;
    }
}