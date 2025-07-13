package com.server.markmyreads.domain.jpa;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public Book(final Clippings clippings, final String title, final String author) {
        this.clippings = clippings;
        this.title = title;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clippings_id", updatable = false, insertable = false)
    private Long clippingsId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clippings_id", nullable = false)
    private Clippings clippings;

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
