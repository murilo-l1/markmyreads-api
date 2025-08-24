package com.server.markmyreads.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = Book.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Book {

    protected static final String TABLE_NAME = "book";

    public Book(final String title, final String author) {
        this.title = title;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 400)
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Size(min = 1, max = 400)
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "last_read_at")
    private LocalDate lastReadAt;

}
