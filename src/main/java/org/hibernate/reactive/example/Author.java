package org.hibernate.reactive.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name="authors")
public class Author {
    @Id @GeneratedValue
    private Integer id;

    @NotNull @Size(max=100)
    private String name;

    @OneToMany(mappedBy = "author", cascade = PERSIST)
    private List<Book> books = new ArrayList<>();

    Author(String name) {
        this.name = name;
    }


    public Author() {

    }

    Integer getId() {
        return id;
    }

    String getName() {
        return name;
    }

    List<Book> getBooks() {
        return books;
    }

}
