package com.server.markmyreads.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Note.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Note {

    protected static final String TABLE_NAME = "note";

    public Note(final Book book, final String content) {
        this.book = book;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", insertable = false, updatable = false)
    private Long bookId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @NotBlank
    @Column(name = "content", nullable = false)
    private String content;

}
